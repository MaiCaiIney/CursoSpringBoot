package com.utn.springboot.billeteravirtual.exception;

import com.utn.springboot.billeteravirtual.model.cuentas.TipoMoneda;

public class SaldoInsuficienteException extends RuntimeException {

    private final Long idCuenta;
    private final Double saldo;
    private final TipoMoneda tipoMoneda;

    public SaldoInsuficienteException(Long idCuenta, Double saldo, TipoMoneda tipoMoneda) {
        this.idCuenta = idCuenta;
        this.saldo = saldo;
        this.tipoMoneda = tipoMoneda;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    @Override
    public String getMessage() {
        return "Saldo insuficiente en la cuenta " + idCuenta + ". Saldo actual: " + saldo + " " + tipoMoneda.getSimbolo();
    }
}
