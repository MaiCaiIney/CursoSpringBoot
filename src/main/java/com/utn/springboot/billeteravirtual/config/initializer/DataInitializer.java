package com.utn.springboot.billeteravirtual.config.initializer;

import com.utn.springboot.billeteravirtual.repository.*;
import com.utn.springboot.billeteravirtual.repository.entity.CredencialEntity;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.DireccionEntity;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.repository.entity.transacciones.*;
import com.utn.springboot.billeteravirtual.types.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final ServicioRepository servicioRepository;
    private final PagoProgramadoRepository pagoProgramadoRepository;
    private final CredencialRepository credencialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository,
                           TransaccionRepository transaccionRepository, ServicioRepository servicioRepository,
                           PagoProgramadoRepository pagoProgramadoRepository, CredencialRepository credencialRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.servicioRepository = servicioRepository;
        this.pagoProgramadoRepository = pagoProgramadoRepository;
        this.credencialRepository = credencialRepository;
    }

    @PostConstruct
    public void init() {
        UsuarioEntity usuario1 = new UsuarioEntity("Juan Perez", "juan.perez@example.com", 35);
        UsuarioEntity usuario2 = new UsuarioEntity("Ana Garcia", "ana.garcia@example.com", 22);
        UsuarioEntity usuario3 = new UsuarioEntity("Jose Morales", "jose.morales@example.com", 45);
        UsuarioEntity usuario4 = new UsuarioEntity("Maria Rodriguez", "maria.rodriguez@example.com", 52);
        UsuarioEntity usuario5 = new UsuarioEntity("Carlos Sanchez", "carlos.sanchez@example.com", 60);
        UsuarioEntity usuario6 = new UsuarioEntity("Laura Fernandez", "laura.fernandez@example.com", 37);

        DireccionEntity direccion1 = new DireccionEntity("Calle 1",
                                                         "123",
                                                         "PB C",
                                                         "Mar del Plata",
                                                         "Buenos Aires",
                                                         TipoDireccion.CASA);
        usuario1.setDireccion(direccion1);

        String password1 = passwordEncoder.encode("profe");
        CredencialEntity credencialEntity1 = new CredencialEntity("profe", password1, usuario1);
        credencialRepository.save(credencialEntity1);

        CredencialEntity credencialEntity2 = new CredencialEntity("alumno", passwordEncoder.encode("pass"), usuario2);
        credencialRepository.save(credencialEntity2);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
        usuarioRepository.save(usuario4);
        usuarioRepository.save(usuario5);
        usuarioRepository.save(usuario6);

        CuentaEntity cuenta1 = new CuentaEntity(TipoCuenta.AHORROS,
                                                "1234567890123456789000",
                                                "alias.cuenta.ahorro",
                                                new BigDecimal(9000),
                                                TipoMoneda.ARS,
                                                usuario1);

        CuentaEntity cuenta2 = new CuentaEntity(TipoCuenta.CORRIENTE,
                                                "1234567890123456789001",
                                                "alias.cuenta.corriente",
                                                new BigDecimal(40000),
                                                TipoMoneda.ARS,
                                                usuario1);

        CuentaEntity cuenta3 = new CuentaEntity(TipoCuenta.CORRIENTE,
                                                "1234567890123456789002",
                                                "alias.cuenta.sueldo",
                                                BigDecimal.ZERO,
                                                TipoMoneda.ARS,
                                                usuario2);

        CuentaEntity cuenta4 = new CuentaEntity(TipoCuenta.AHORROS,
                                                "1234567890123456789003",
                                                "alias.cuenta.servicio",
                                                BigDecimal.ZERO,
                                                TipoMoneda.ARS,
                                                usuario3);

        cuentaRepository.save(cuenta1);
        cuentaRepository.save(cuenta2);
        cuentaRepository.save(cuenta3);
        cuentaRepository.save(cuenta4);

        ServicioEntity servicio1 = new ServicioEntity("Luz", "EDEA", "301234567800");
        servicioRepository.save(servicio1);

        // Deposito $100000.00 en cuenta2
        TransaccionEntity deposito1 = new TransaccionEntity(TipoTransaccion.DEPOSITO, new BigDecimal("100000.00"), cuenta2);
        transaccionRepository.save(deposito1);

        // Pago de servicio $50000.00 en cuenta2
        TransaccionEntity pagoServicioEntity1 = new PagoServicioEntity(new BigDecimal("50000.00"), cuenta2, servicio1);
        transaccionRepository.save(pagoServicioEntity1);

        // Transferencia de $10000.00 de cuenta2 a cuenta1
        TransaccionEntity transferencia1 = new TransferenciaEntity(new BigDecimal("10000.00"), cuenta2, cuenta1);
        transaccionRepository.save(transferencia1);

        // Retiro de $1000.00 en cuenta1
        TransaccionEntity transaccionEntity = new TransaccionEntity(TipoTransaccion.RETIRO, new BigDecimal("1000.00"), cuenta1);
        transaccionRepository.save(transaccionEntity);

        PagoProgramadoEntity.Builder pagoProgramadoBuilder = new PagoProgramadoEntity.Builder();
        pagoProgramadoBuilder.setUsuario(usuario1);
        pagoProgramadoBuilder.setCuentaOrigen(cuenta1);
        pagoProgramadoBuilder.setCuentaDestino(cuenta3);
        pagoProgramadoBuilder.setMonto(new BigDecimal("200000.00"));
        pagoProgramadoBuilder.setTipoPago(TipoPagoProgramado.SUELDO);
        pagoProgramadoBuilder.setFrecuencia(FrecuenciaPagoProgramado.MENSUAL);
        pagoProgramadoBuilder.setFechaInicio(LocalDate.of(2023, 10, 20).atStartOfDay());
        pagoProgramadoBuilder.setEstado(EstadoPagoProgramado.ACTIVO);
        pagoProgramadoBuilder.setProximaEjecucion(LocalDate.of(2023, 10, 22).atStartOfDay());
        pagoProgramadoRepository.save(pagoProgramadoBuilder.build());

        PagoProgramadoEntity.Builder pagoProgramadoBuilder2 = new PagoProgramadoEntity.Builder();
        pagoProgramadoBuilder2.setUsuario(usuario1);
        pagoProgramadoBuilder2.setCuentaOrigen(cuenta2);
        pagoProgramadoBuilder2.setCuentaDestino(cuenta4);
        pagoProgramadoBuilder2.setMonto(new BigDecimal("5000.00"));
        pagoProgramadoBuilder2.setTipoPago(TipoPagoProgramado.SERVICIO);
        pagoProgramadoBuilder2.setFrecuencia(FrecuenciaPagoProgramado.SEMANAL);
        pagoProgramadoBuilder2.setFechaInicio(LocalDate.of(2023, 10, 20).atStartOfDay());
        pagoProgramadoBuilder2.setEstado(EstadoPagoProgramado.ACTIVO);
        pagoProgramadoBuilder2.setProximaEjecucion(LocalDate.of(2023, 10, 22).atStartOfDay());
        pagoProgramadoRepository.save(pagoProgramadoBuilder2.build());
    }
}
