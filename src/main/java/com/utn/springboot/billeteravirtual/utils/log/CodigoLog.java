package com.utn.springboot.billeteravirtual.utils.log;

public enum CodigoLog {
    CREAR_USUARIO("CREAR_USUARIO", "Usuario creado con éxito"),
    ACTUALIZAR_USUARIO("ACTUALIZAR_USUARIO", "Usuario actualizado con éxito"),
    ELIMINAR_USUARIO("ELIMINAR_USUARIO", "Usuario eliminado con éxito");

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
