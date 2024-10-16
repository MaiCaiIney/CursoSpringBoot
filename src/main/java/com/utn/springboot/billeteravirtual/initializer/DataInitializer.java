package com.utn.springboot.billeteravirtual.initializer;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.entity.DireccionEntity;
import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.PagoServicioEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.ServicioEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.TransferenciaEntity;
import com.utn.springboot.billeteravirtual.repository.CuentaRepository;
import com.utn.springboot.billeteravirtual.repository.ServicioRepository;
import com.utn.springboot.billeteravirtual.repository.TransaccionRepository;
import com.utn.springboot.billeteravirtual.repository.UsuarioRepository;
import com.utn.springboot.billeteravirtual.types.TipoCuenta;
import com.utn.springboot.billeteravirtual.types.TipoDireccion;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final ServicioRepository servicioRepository;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository,
                           TransaccionRepository transaccionRepository, ServicioRepository servicioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.servicioRepository = servicioRepository;
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
        cuentaRepository.save(cuenta1);
        cuentaRepository.save(cuenta2);

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
    }
}
