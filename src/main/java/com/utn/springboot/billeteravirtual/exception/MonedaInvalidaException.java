package com.utn.springboot.billeteravirtual.exception;

import com.utn.springboot.billeteravirtual.types.TipoMoneda;

public class MonedaInvalidaException extends RuntimeException {

    private final TipoOperacion tipoOperacion;
    private final TipoMoneda tipoMoneda;
    private final Long idCuenta;

    public MonedaInvalidaException(TipoOperacion tipoOperacion, TipoMoneda tipoMoneda, Long idCuenta) {
        this.tipoOperacion = tipoOperacion;
        this.tipoMoneda = tipoMoneda;
        this.idCuenta = idCuenta;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    @Override
    public String getMessage() {
        return String.format("No se puede realizar la operación %s en la cuenta %d con moneda %s.", tipoOperacion, idCuenta, tipoMoneda);
    }
}
