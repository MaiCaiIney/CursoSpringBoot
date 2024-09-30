package com.utn.springboot.billeteravirtual.model.cuentas;

public enum TipoMoneda {
    ARS("$"), USD("U$D");

    private final String simbolo;

    TipoMoneda(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
