package com.utn.springboot.billeteravirtual.initializer;

import com.utn.springboot.billeteravirtual.entity.*;
import com.utn.springboot.billeteravirtual.entity.transacciones.PagoServicioEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.ServicioEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.repository.*;
import com.utn.springboot.billeteravirtual.repository.PagoServicioRepository;
import com.utn.springboot.billeteravirtual.repository.TransaccionRepository;
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
    private final PagoServicioRepository pagoServicioRepository;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository,
                           TransaccionRepository transaccionRepository, ServicioRepository servicioRepository,
                           PagoServicioRepository pagoServicioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.servicioRepository = servicioRepository;
        this.pagoServicioRepository = pagoServicioRepository;
    }

    @PostConstruct
    public void init() {
        UsuarioEntity usuario1 = new UsuarioEntity("Juan Perez", "juan.perez@example.com", 35);
        UsuarioEntity usuario2 = new UsuarioEntity("Ana Garcia", "ana.garcia@example.com", 22);
        UsuarioEntity usuario3 = new UsuarioEntity("Jose Morales", "jose.morales@example.com", 45);

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

        CuentaEntity cuenta1 = new CuentaEntity(TipoCuenta.AHORROS,
                                                "1234567890123456789000",
                                                "alias.cuenta.ahorro",
                                                BigDecimal.ZERO,
                                                TipoMoneda.ARS,
                                                usuario1);

        CuentaEntity cuenta2 = new CuentaEntity(TipoCuenta.CORRIENTE,
                                                "1234567890123456789001",
                                                "alias.cuenta.corriente",
                                                new BigDecimal("50000.00"),
                                                TipoMoneda.ARS,
                                                usuario2);
        cuentaRepository.save(cuenta1);
        cuentaRepository.save(cuenta2);

        TransaccionEntity deposito1 = new TransaccionEntity(TipoTransaccion.DEPOSITO,
                                                            new BigDecimal("100000.00"),
                                                            cuenta2);
        transaccionRepository.save(deposito1);

        ServicioEntity servicio1 = new ServicioEntity("Luz", "EDEA", "301234567800");
        servicioRepository.save(servicio1);

        PagoServicioEntity pagoServicioEntity1 = new PagoServicioEntity(new BigDecimal("50000.00"),
                                                                        cuenta2,
                                                                        servicio1);
        transaccionRepository.save(pagoServicioEntity1);
    }
}
