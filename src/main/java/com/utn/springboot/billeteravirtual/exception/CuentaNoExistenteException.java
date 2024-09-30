package com.utn.springboot.billeteravirtual.exception;

public class CuentaNoExistenteException extends RuntimeException {
    public CuentaNoExistenteException(Long id) {
        super("Cuenta con ID " + id + " no existe.");
    }
}
