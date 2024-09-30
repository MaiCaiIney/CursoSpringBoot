package com.utn.springboot.billeteravirtual.exception;

public class MonedaInvalidaException extends RuntimeException {

    private final TipoOperacion tipoOperacion;

    public MonedaInvalidaException(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    @Override
    public String getMessage() {
        return "Operación inválida: " + tipoOperacion + ". No se puede realizar la operación con diferente tipo de moneda.";
    }
}
