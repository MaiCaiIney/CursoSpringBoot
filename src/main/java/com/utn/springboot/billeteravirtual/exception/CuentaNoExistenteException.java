package com.utn.springboot.billeteravirtual.exception;

public class CuentaNoExistenteException extends RuntimeException {
    private final Long id;
    private Long idUsuario;

    public CuentaNoExistenteException(Long id) {
        super("Cuenta con ID " + id + " no existe.");
        this.id = id;
    }

    public CuentaNoExistenteException(Long id, Long idUsuario) {
        super(String.format("Cuenta con ID %d no existe para el usuario con ID %d.", id, idUsuario));
        this.id = id;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}
