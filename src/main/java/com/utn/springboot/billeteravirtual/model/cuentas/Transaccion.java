package com.utn.springboot.billeteravirtual.model.cuentas;

import com.utn.springboot.billeteravirtual.types.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaccion(TipoTransaccion tipo, BigDecimal monto, LocalDateTime fecha) implements Comparable<Transaccion> {

    @Override
    public int compareTo(Transaccion transaccion) {
        return this.fecha.compareTo(transaccion.fecha);
    }
}
