package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.exception.MonedaInvalidaException;
import com.utn.springboot.billeteravirtual.exception.PagoNoExistenteExcepcion;
import com.utn.springboot.billeteravirtual.exception.TipoOperacion;
import com.utn.springboot.billeteravirtual.model.cuentas.PagoProgramado;
import com.utn.springboot.billeteravirtual.repository.PagoProgramadoRepository;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.repository.entity.transacciones.PagoProgramadoEntity;
import com.utn.springboot.billeteravirtual.types.EstadoPagoProgramado;
import com.utn.springboot.billeteravirtual.types.FrecuenciaPagoProgramado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "scheduler.pagos.habilitado", havingValue = "true", matchIfMissing = true)
public class PagoProgramadoService {
    private final PagoProgramadoRepository pagoProgramadoRepository;
    private final CuentaService cuentaService;
    private final UsuarioService usuarioService;

//    @Value("${scheduler.pagos.cron}")
//    private String schedulerCron;

    @Autowired
    public PagoProgramadoService(PagoProgramadoRepository pagoProgramadoRepository, CuentaService cuentaService,
                                 UsuarioService usuarioService) {
        this.pagoProgramadoRepository = pagoProgramadoRepository;
        this.cuentaService = cuentaService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public PagoProgramado programarPago(PagoProgramado pagoProgramado, Long usuarioId) {
        PagoProgramadoEntity.Builder builder = new PagoProgramadoEntity.Builder();

        UsuarioEntity usuario = usuarioService.buscarEntidadPorId(usuarioId);
        builder.setUsuario(usuario);

        CuentaEntity cuentaOrigen = cuentaService.buscarEntidadPorId(pagoProgramado.getIdCuentaOrigen());
        builder.setCuentaOrigen(cuentaOrigen);

        CuentaEntity cuentaDestino = cuentaService.buscarEntidadPorId(pagoProgramado.getIdCuentaDestino());
        builder.setCuentaDestino(cuentaDestino);

        if (esMonedaInvalida(cuentaOrigen, cuentaDestino)) {
            throw new MonedaInvalidaException(TipoOperacion.PAGO_PROGRAMADO, cuentaOrigen.getTipoMoneda(), cuentaOrigen.getId());
        }

        builder.setTipoPago(pagoProgramado.getTipoPagoProgramado());
        builder.setMonto(pagoProgramado.getMonto());
        builder.setFrecuencia(pagoProgramado.getPeriodicidad());
        builder.setFechaInicio(pagoProgramado.getFechaInicio());
        builder.setEstado(EstadoPagoProgramado.ACTIVO);
        builder.setProximaEjecucion(calcularProximaEjecucion(pagoProgramado.getFechaInicio(), pagoProgramado.getPeriodicidad()));

        PagoProgramadoEntity entity = pagoProgramadoRepository.save(builder.build());
        return mapEntityToModel(entity);
    }

    public List<PagoProgramado> obtenerPagosProgramadosActivos(Long usuarioId) {
        return pagoProgramadoRepository.findByEstado(EstadoPagoProgramado.ACTIVO).stream().map(this::mapEntityToModel).toList();
    }

    public List<PagoProgramado> obtenerPagosProgramadosEjecutados(Long usuarioId) {
        List<PagoProgramadoEntity> entities = pagoProgramadoRepository.findByEstadoIn(List.of(EstadoPagoProgramado.COMPLETADO,
                                                                                              EstadoPagoProgramado.FALLIDO));
        return entities.stream().map(this::mapEntityToModel).toList();
    }

    @Transactional
    public PagoProgramado actualizarMonto(Long pagoId, BigDecimal monto) {
        PagoProgramadoEntity pagoProgramadoEntity =
                pagoProgramadoRepository.findById(pagoId).orElseThrow(() -> new PagoNoExistenteExcepcion(pagoId));
        pagoProgramadoEntity.setMonto(monto);
        pagoProgramadoEntity = pagoProgramadoRepository.save(pagoProgramadoEntity);
        return mapEntityToModel(pagoProgramadoEntity);
    }

    @Transactional
    public void cancelarPagoProgramado(Long pagoId) {
        PagoProgramadoEntity pagoProgramadoEntity =
                pagoProgramadoRepository.findById(pagoId).orElseThrow(() -> new PagoNoExistenteExcepcion(pagoId));
        pagoProgramadoEntity.setEstado(EstadoPagoProgramado.CANCELADO);
        pagoProgramadoRepository.save(pagoProgramadoEntity);
    }

    @Async
//    @Scheduled(fixedRate = 60000, initialDelay = 30000) // Espera 30 segundos antes de ejecutar la primera vez
//    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta todos los días a la medianoche
    @Scheduled(cron = "${scheduler.pagos.cron}")
    public void ejecutarPagosProgramados() {
        LocalDateTime hoy = LocalDateTime.now();

        // 1. Obtener los pagos programados que deben ser ejecutados hoy o antes
        List<PagoProgramadoEntity> pagos = pagoProgramadoRepository.findByEstadoAndProximaEjecucionBefore(EstadoPagoProgramado.ACTIVO, hoy);

        for (PagoProgramadoEntity pago : pagos) {
            try {
                // 2. Realizar la transferencia de fondos
                cuentaService.transferirPagoProgramado(pago.getCuentaOrigen().getId(), pago.getCuentaDestino().getId(), pago.getMonto(),
                                                       pago.getCuentaOrigen().getTipoMoneda());

                // 3. Marcar la ejecución como completada
                pago.setEstado(EstadoPagoProgramado.COMPLETADO);
            } catch (Exception e) {
                // Manejar excepciones inesperadas y marcar la ejecución como fallida
                pago.setEstado(EstadoPagoProgramado.FALLIDO);
                pago.setLogEjecucion(e.getMessage());
            }

            // 4. Actualizar la fecha de última ejecución
            pago.setUltimaEjecucion(hoy);

            // 5. Guardar el estado actualizado de la ejecución del pago programado
            pagoProgramadoRepository.save(pago);

            // 6. Programar la próxima ejecución
            PagoProgramadoEntity proximaEjecucion = new PagoProgramadoEntity(pago);
            proximaEjecucion.setEstado(EstadoPagoProgramado.ACTIVO);

            // 7. Calcular la próxima fecha de ejecución
            LocalDateTime next = calcularProximaEjecucion(hoy, proximaEjecucion.getFrecuencia());
            proximaEjecucion.setProximaEjecucion(next);

            pagoProgramadoRepository.save(proximaEjecucion);
        }
    }

    private LocalDateTime calcularProximaEjecucion(LocalDateTime fecha, FrecuenciaPagoProgramado frecuencia) {
        return switch (frecuencia) {
            case DIARIA -> fecha.plusDays(1).toLocalDate().atStartOfDay();
            case SEMANAL -> fecha.plusWeeks(1).toLocalDate().atStartOfDay();
            case QUINCENAL -> fecha.plusWeeks(2).toLocalDate().atStartOfDay();
            case MENSUAL -> fecha.plusMonths(1).toLocalDate().atStartOfDay();
        };
    }

    private PagoProgramado mapEntityToModel(PagoProgramadoEntity entity) {
        PagoProgramado.Builder pagoProgramado = new PagoProgramado.Builder()
                .setId(entity.getId())
                .setTipoPago(entity.getTipoPago())
                .setMonto(entity.getMonto())
                .setFechaInicio(entity.getFechaInicio())
                .setPeriodicidad(entity.getFrecuencia())
                .setFechaUltimaEjecucion(entity.getUltimaEjecucion())
                .setFechaProximaEjecucion(entity.getProximaEjecucion())
                .setIdCuentaOrigen(entity.getCuentaOrigen().getId())
                .setIdCuentaDestino(entity.getCuentaDestino().getId());

        return pagoProgramado.build();
    }

    private boolean esMonedaInvalida(CuentaEntity cuentaOrigen, CuentaEntity cuentaDestino) {
        return !cuentaOrigen.getTipoMoneda().equals(cuentaDestino.getTipoMoneda());
    }
}
