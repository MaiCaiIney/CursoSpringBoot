package com.utn.springboot.billeteravirtual.model.cuentas.movimientos;

public enum TipoMovimiento {
    DEPOSITO("DEPOSITO DE DINERO"), RETIRO("RETIRO DE DINERO"), TRANSFERENCIA("TRANSFERENCIA DE DINERO");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
