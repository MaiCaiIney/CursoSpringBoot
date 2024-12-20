package com.utn.springboot.billeteravirtual.model.mapper;

import com.utn.springboot.billeteravirtual.controller.dto.CuentaRequest;
import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.projections.TransaccionProjection;
import com.utn.springboot.billeteravirtual.repository.entity.transacciones.TransaccionEntity;
import org.springframework.stereotype.Component;

// Un mapper es una clase que se utiliza para convertir un objeto de un tipo en otro.
// En este caso, CuentaMapper se utiliza para convertir un objeto CuentaRequest en un objeto Cuenta.
// CuentaRequest es un objeto que se utiliza para recibir los datos de una cuenta a través de una petición HTTP.
// Cuenta es un objeto que se utiliza para representar una cuenta en el sistema.
// Luego, existirá una clase CuentaEntity que se utilizará para representar una cuenta en la base de datos.
@Component
public class CuentaMapper {
    public Cuenta toModel(CuentaRequest dto) {
        Cuenta.CuentaBuilder cuenta = new Cuenta.CuentaBuilder()
                .tipoCuenta(dto.getTipoCuenta())
                .tipoMoneda(dto.getTipoMoneda())
                .balance(dto.getBalance());
        return cuenta.build();
    }

    public Cuenta toModel(CuentaEntity entity) {
        Cuenta.CuentaBuilder cuenta = new Cuenta.CuentaBuilder()
                .id(entity.getId())
                .cbu(entity.getCbu())
                .alias(entity.getAlias())
                .tipoCuenta(entity.getTipoCuenta())
                .tipoMoneda(entity.getTipoMoneda())
                .balance(entity.getBalance());
        return cuenta.build();
    }

    public Transaccion toModel(TransaccionEntity entity) {
        return new Transaccion(
                entity.getTipoTransaccion(),
                entity.getMonto(),
                entity.getFechaTransaccion()
        );
    }

    public Transaccion toModel(TransaccionProjection projection) {
        return new Transaccion(
                projection.getTipoTransaccion(),
                projection.getMonto(),
                projection.getFechaTransaccion()
        );
    }
}
