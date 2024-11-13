package com.utn.springboot.billeteravirtual.config.initializer;

import com.utn.springboot.billeteravirtual.repository.*;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.DireccionEntity;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.repository.entity.security.CredencialEntity;
import com.utn.springboot.billeteravirtual.repository.entity.security.PerfilEntity;
import com.utn.springboot.billeteravirtual.repository.entity.security.PermisoEntity;
import com.utn.springboot.billeteravirtual.repository.entity.security.RolEntity;
import com.utn.springboot.billeteravirtual.repository.entity.transacciones.*;
import com.utn.springboot.billeteravirtual.types.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final ServicioRepository servicioRepository;
    private final PagoProgramadoRepository pagoProgramadoRepository;
    private final CredencialRepository credencialRepository;
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PerfilRepository perfilRepository;
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
        PermisoEntity permiso1 = new PermisoEntity(PermisoUsuario.CREAR_USUARIO, "Dar de alta un nuevo usuario en el sistema");
        permiso1 = permisoRepository.save(permiso1);
        PermisoEntity permiso2 = new PermisoEntity(PermisoUsuario.ELIMINAR_USUARIO, "Eliminar un usuario del sistema");
        permiso2 = permisoRepository.save(permiso2);
        PermisoEntity permiso3 = new PermisoEntity(PermisoUsuario.VER_TRANSACCIONES, "Visualizar las transacciones de una cuenta");
        permiso3 = permisoRepository.save(permiso3);
        PermisoEntity permiso4 = new PermisoEntity(PermisoUsuario.EDITAR_CUENTA, "Modificar los datos de una cuenta");
        permiso4 = permisoRepository.save(permiso4);
        PermisoEntity permiso5 = new PermisoEntity(PermisoUsuario.DEPOSITAR_FONDOS, "Realizar un depósito en una cuenta");
        permiso5 = permisoRepository.save(permiso5);
        PermisoEntity permiso6 = new PermisoEntity(PermisoUsuario.TRANSFERIR_FONDOS, "Transferir fondos entre cuentas");
        permiso6 = permisoRepository.save(permiso6);
        PermisoEntity permiso7 = new PermisoEntity(PermisoUsuario.RETIRAR_FONDOS, "Retirar fondos de una cuenta");
        permiso7 = permisoRepository.save(permiso7);
        PermisoEntity permiso8 = new PermisoEntity(PermisoUsuario.PROGRAMAR_PAGO, "Programar un pago");
        permiso8 = permisoRepository.save(permiso8);
        PermisoEntity permiso9 = new PermisoEntity(PermisoUsuario.VER_PAGOS_PROGRAMADOS, "Visualizar los pagos programados");
        permiso9 = permisoRepository.save(permiso9);

        // El rol de usuario tiene permisos para ver transacciones, depositar fondos, transferir fondos y retirar fondos
        RolEntity rolUser = new RolEntity(RolUsuario.ROLE_USUARIO);
        rolUser.agregarPermiso(permiso3);
        rolUser.agregarPermiso(permiso5);
        rolUser.agregarPermiso(permiso6);
        rolUser.agregarPermiso(permiso7);
        rolUser = rolRepository.save(rolUser);

        // El rol de administrador tiene permisos para crear y eliminar usuarios, y programar pagos
        RolEntity rolAdmin = new RolEntity(RolUsuario.ADMIN);
        rolAdmin.agregarPermiso(permiso1);
        rolAdmin.agregarPermiso(permiso2);
        rolAdmin.agregarPermiso(permiso8);
        rolAdmin = rolRepository.save(rolAdmin);

        // El rol de manager tiene permisos para editar cuentas y programar pagos
        RolEntity rolManager = new RolEntity(RolUsuario.MANAGER);
        rolManager.agregarPermiso(permiso4);
        rolManager.agregarPermiso(permiso8);
        rolManager = rolRepository.save(rolManager);

        // El rol de auditor tiene permisos para ver transacciones y ver pagos programados
        RolEntity rolAuditor = new RolEntity(RolUsuario.AUDITOR);
        rolAuditor.agregarPermiso(permiso3);
        rolAuditor.agregarPermiso(permiso9);
        rolAuditor = rolRepository.save(rolAuditor);

        PerfilEntity perfilUsuario = new PerfilEntity(PerfilUsuario.PERFIL_USUARIO);
        perfilUsuario.agregarRol(rolUser);
        perfilUsuario = perfilRepository.save(perfilUsuario);

        PerfilEntity perfilAdmin = new PerfilEntity(PerfilUsuario.PERFIL_ADMIN);
        perfilAdmin.agregarRol(rolAdmin);
        perfilAdmin.agregarRol(rolManager);
        perfilAdmin.agregarRol(rolAuditor);
        perfilAdmin = perfilRepository.save(perfilAdmin);

        PerfilEntity perfilSupervisor = new PerfilEntity(PerfilUsuario.PERFIL_SUPERVISOR);
        perfilSupervisor.agregarRol(rolManager);
        perfilSupervisor.agregarRol(rolAuditor);
        perfilSupervisor = perfilRepository.save(perfilSupervisor);

        PerfilEntity perfilAnalista = new PerfilEntity(PerfilUsuario.PERFIL_ANALISTA);
        perfilAnalista.agregarRol(rolAuditor);
        perfilAnalista = perfilRepository.save(perfilAnalista);

        UsuarioEntity usuario1 = new UsuarioEntity("Mai Berterreche", "maicaberterreche@gmail.com", 34);
        UsuarioEntity usuario2 = new UsuarioEntity("Ana Garcia", "ana.garcia@example.com", 22);
        UsuarioEntity usuario3 = new UsuarioEntity("Jose Morales", "jose.morales@example.com", 45);
        UsuarioEntity usuario4 = new UsuarioEntity("Maria Rodriguez", "maria.rodriguez@example.com", 52);
        UsuarioEntity usuario5 = new UsuarioEntity("Carlos Sanchez", "carlos.sanchez@example.com", 60);
        UsuarioEntity usuario6 = new UsuarioEntity("Laura Fernandez", "laura.fernandez@example.com", 37);

        CredencialEntity credencialEntity1 = new CredencialEntity("profe", passwordEncoder.encode("profe"), usuario1, perfilAdmin);
        credencialRepository.save(credencialEntity1);

        CredencialEntity credencialEntity2 = new CredencialEntity("alumno", passwordEncoder.encode("alumno"), usuario2, perfilAnalista);
        credencialEntity2.agregarRol(rolManager);
        credencialRepository.save(credencialEntity2);

        CredencialEntity credencialEntity3 = new CredencialEntity("jose", passwordEncoder.encode("jose"), usuario3, perfilUsuario);
        credencialRepository.save(credencialEntity3);

        CredencialEntity credencialEntity4 = new CredencialEntity("maria", passwordEncoder.encode("maria"), usuario4, perfilUsuario);
        credencialRepository.save(credencialEntity4);

        CredencialEntity credencialEntity5 = new CredencialEntity("carlos", passwordEncoder.encode("carlos"), usuario5, perfilUsuario);
        credencialRepository.save(credencialEntity5);

        CredencialEntity credencialEntity6 = new CredencialEntity("laura", passwordEncoder.encode("laura"), usuario6, perfilUsuario);
        credencialRepository.save(credencialEntity6);

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
