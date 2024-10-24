package com.utn.springboot.billeteravirtual.exception;

public class PagoNoExistenteExcepcion extends RuntimeException {
    private final Long id;

    public PagoNoExistenteExcepcion(Long id) {
        super("No se encontro el pago programado con id " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
