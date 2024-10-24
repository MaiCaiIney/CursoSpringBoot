package com.utn.springboot.billeteravirtual.types;

public enum TipoTransaccion {
    DEPOSITO("DEPOSITO DE DINERO"),
    RETIRO("RETIRO DE DINERO"),
    TRANSFERENCIA("TRANSFERENCIA DE DINERO"),
    PAGO_SERVICIO("PAGO DE SERVICIO"),
    PAGO_PROGRAMADO("PAGO PROGRAMADO");

    private final String descripcion;

    TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
