package com.utn.springboot.billeteravirtual.model.cuentas;

import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion implements Comparable<Transaccion> {
    private final Long id;

    private final TipoTransaccion tipo;

    private final TipoMoneda tipoMoneda;

    private final BigDecimal monto;

    private final LocalDateTime fecha;

    public Transaccion(Long id, TipoTransaccion tipo, TipoMoneda tipoMoneda, BigDecimal monto, LocalDateTime fecha) {
        this.id = id;
        this.tipo = tipo;
        this.tipoMoneda = tipoMoneda;
        this.monto = monto;
        this.fecha = fecha;
    }

    @Override
    public int compareTo(Transaccion transaccion) {
        return this.fecha.compareTo(transaccion.fecha);
    }
}
