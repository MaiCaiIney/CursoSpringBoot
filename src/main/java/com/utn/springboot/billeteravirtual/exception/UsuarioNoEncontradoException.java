package com.utn.springboot.billeteravirtual.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    private final Long id;

    public UsuarioNoEncontradoException(Long id) {
        super("Usuario con ID " + id + " no encontrado.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
