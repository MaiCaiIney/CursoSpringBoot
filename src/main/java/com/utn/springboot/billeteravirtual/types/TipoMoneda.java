package com.utn.springboot.billeteravirtual.types;

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
