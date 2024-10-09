package com.utn.springboot.billeteravirtual.exception;

import com.utn.springboot.billeteravirtual.types.TipoMoneda;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {

    private final Long idCuenta;
    private final BigDecimal saldo;
    private final TipoMoneda tipoMoneda;

    public SaldoInsuficienteException(Long idCuenta, BigDecimal saldo, TipoMoneda tipoMoneda) {
        this.idCuenta = idCuenta;
        this.saldo = saldo;
        this.tipoMoneda = tipoMoneda;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public BigDecimal getSaldo() {
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
