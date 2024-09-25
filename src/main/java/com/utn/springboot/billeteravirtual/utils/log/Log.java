package com.utn.springboot.billeteravirtual.utils.log;

public interface Log {
    void registrarAccion(CodigoLog codigoLog);
    <T> void registrarAccion(CodigoLog codigoLog, T object);
}
