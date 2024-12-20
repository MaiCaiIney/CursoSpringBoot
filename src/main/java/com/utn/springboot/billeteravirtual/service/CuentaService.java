package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.exception.CuentaNoExistenteException;
import com.utn.springboot.billeteravirtual.exception.MonedaInvalidaException;
import com.utn.springboot.billeteravirtual.exception.SaldoInsuficienteException;
import com.utn.springboot.billeteravirtual.exception.TipoOperacion;
import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import com.utn.springboot.billeteravirtual.model.mapper.CuentaMapper;
import com.utn.springboot.billeteravirtual.repository.CuentaRepository;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @Autowired
    @Lazy
    private TransaccionService transaccionService;

    @Autowired
    @Lazy
    private CuentaMapper cuentaMapper;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    // Crear una cuenta.
    // Si al momento de crear la cuenta el balance es mayor a 0, se creará una transacción inicial de tipo DEPOSITO.
    public Cuenta crearCuenta(Cuenta cuenta, Long idUsuario) {
        UsuarioEntity usuario = usuarioService.buscarEntidadPorId(idUsuario);
        CuentaEntity entity = new CuentaEntity();
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setTipoMoneda(cuenta.getTipoMoneda());
        entity.setBalance(cuenta.getBalance());
        entity.setUsuario(usuario);
        entity = cuentaRepository.save(entity);

        return cuentaMapper.toModel(entity);
    }

    // Obtener todas las cuentas de un usuario
    public List<Cuenta> obtenerCuentasPorUsuario(Long idUsuario) {
        List<CuentaEntity> entidades = cuentaRepository.findByUsuarioId(idUsuario);
        return entidades.stream().map(cuentaMapper::toModel).toList();
    }

    // Obtener una cuenta por ID. Si no existe, lanzará una excepción CuentaNoExistenteException.
    public Cuenta obtenerCuentaPorId(Long id) throws CuentaNoExistenteException {
        CuentaEntity entity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
        return cuentaMapper.toModel(entity);
    }

    // Depósito de dinero en una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda del depósito no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el depósito es exitoso, se creará una transacción de tipo DEPOSITO.
    @Transactional
    public Transaccion depositar(Long cuentaId, BigDecimal monto, TipoMoneda moneda) throws CuentaNoExistenteException,
            MonedaInvalidaException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNoExistenteException(cuentaId));

        if (!cuentaEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.DEPOSITO, moneda, cuentaId);
        }

        cuentaEntity.setBalance(cuentaEntity.getBalance().add(monto));
        cuentaRepository.save(cuentaEntity);


        return transaccionService.registrarTransaccion(cuentaId, TipoTransaccion.DEPOSITO, monto);
    }

    // Retiro de dinero de una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda del retiro no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si el retiro es exitoso, se creará una transacción de tipo RETIRO.
    @Transactional
    public Transaccion retirar(Long cuentaId, BigDecimal monto, TipoMoneda moneda) throws CuentaNoExistenteException,
            MonedaInvalidaException,
            SaldoInsuficienteException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNoExistenteException(cuentaId));

        if (!cuentaEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.RETIRO, moneda, cuentaId);
        } else if (esBalanceInsuficiente(cuentaEntity.getBalance(), monto)) {
            throw new SaldoInsuficienteException(cuentaId, cuentaEntity.getBalance(), moneda);
        } else {
            cuentaEntity.setBalance(cuentaEntity.getBalance().subtract(monto));
            cuentaRepository.save(cuentaEntity);

            return transaccionService.registrarTransaccion(cuentaId, TipoTransaccion.RETIRO, monto);
        }
    }

    // Transferencia de dinero entre cuentas.
    // Si la cuenta origen o destino no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda de la transferencia no coincide con la moneda de alguna de las cuentas, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta origen es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si la transferencia es exitosa, se creará una transacción de tipo TRANSFERENCIA en ambas cuentas.
    @Transactional
    public Transaccion transferir(Long idOrigen, Long idDestino, BigDecimal monto, TipoMoneda moneda) {
        CuentaEntity cuentaOrigenEntity = cuentaRepository.findById(idOrigen).orElseThrow(() -> new CuentaNoExistenteException(idOrigen));
        CuentaEntity cuentaDestinoEntity =
                cuentaRepository.findById(idDestino).orElseThrow(() -> new CuentaNoExistenteException(idDestino));

        if (!cuentaOrigenEntity.getTipoMoneda().equals(moneda) || !cuentaDestinoEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.TRANSFERENCIA, moneda, idOrigen);
        } else if (esBalanceInsuficiente(cuentaOrigenEntity.getBalance(), monto)) {
            throw new SaldoInsuficienteException(idOrigen, cuentaOrigenEntity.getBalance(), moneda);
        } else {
            BigDecimal nuevoBalanceOrigen = cuentaOrigenEntity.getBalance().subtract(monto);
            cuentaOrigenEntity.setBalance(nuevoBalanceOrigen);

            BigDecimal nuevoBalanceDestino = cuentaDestinoEntity.getBalance().add(monto);
            cuentaDestinoEntity.setBalance(nuevoBalanceDestino);

            cuentaRepository.save(cuentaOrigenEntity);
            cuentaRepository.save(cuentaDestinoEntity);

            transaccionService.registrarTransaccion(idDestino, TipoTransaccion.TRANSFERENCIA, monto);

            return transaccionService.registrarTransaccion(idOrigen, TipoTransaccion.TRANSFERENCIA, monto);
        }
    }

    @Transactional
    public void transferirPagoProgramado(Long idOrigen, Long idDestino, BigDecimal monto, TipoMoneda moneda) {
        CuentaEntity cuentaOrigenEntity = cuentaRepository.findById(idOrigen).orElseThrow(() -> new CuentaNoExistenteException(idOrigen));
        CuentaEntity cuentaDestinoEntity =
                cuentaRepository.findById(idDestino).orElseThrow(() -> new CuentaNoExistenteException(idDestino));

        if (!cuentaOrigenEntity.getTipoMoneda().equals(moneda) || !cuentaDestinoEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.PAGO_PROGRAMADO, moneda, idOrigen);
        } else if (esBalanceInsuficiente(cuentaOrigenEntity.getBalance(), monto)) {
            throw new SaldoInsuficienteException(idOrigen, cuentaOrigenEntity.getBalance(), moneda);
        } else {
            BigDecimal nuevoBalanceOrigen = cuentaOrigenEntity.getBalance().subtract(monto);
            cuentaOrigenEntity.setBalance(nuevoBalanceOrigen);

            BigDecimal nuevoBalanceDestino = cuentaDestinoEntity.getBalance().add(monto);
            cuentaDestinoEntity.setBalance(nuevoBalanceDestino);

            cuentaRepository.save(cuentaOrigenEntity);
            cuentaRepository.save(cuentaDestinoEntity);

            transaccionService.registrarTransaccion(idOrigen, TipoTransaccion.PAGO_PROGRAMADO, monto);
        }
    }

    // Eliminar una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Devolverá true si la cuenta fue eliminada, false en caso contrario.
    public void eliminarCuenta(Long id) throws CuentaNoExistenteException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
        cuentaRepository.delete(cuentaEntity);
    }

    public List<Transaccion> buscarTransacciones(Long id) throws CuentaNoExistenteException {
        return transaccionService.obtenerTransaccionesPorCuenta(id);
    }

    protected CuentaEntity buscarEntidadPorId(Long id) throws CuentaNoExistenteException {
        return cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
    }

    private boolean esBalanceInsuficiente(BigDecimal saldoDisponible, BigDecimal montoTransaccion) {
        return saldoDisponible.compareTo(montoTransaccion) < 0;
    }
}
