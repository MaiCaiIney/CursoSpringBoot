package com.utn.springboot.billeteravirtual.utils.log;

public enum CodigoLog {
    USUARIO_CREADO("USUARIO_CREADO", "Usuario creado con éxito"),
    USUARIO_ACTUALIZADO("USUARIO_ACTUALIZADO", "Usuario actualizado con éxito"),
    USUARIO_ELIMINADO("USUARIO_ELIMINADO", "Usuario eliminado con éxito"),
    USUARIO_NO_ENCONTRADO("USUARIO_NO_ENCONTRADO", "Usuario no encontrado");

    private final String codigo;
    private final String descripcion;

    CodigoLog(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
