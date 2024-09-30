package com.utn.springboot.billeteravirtual.model.cuentas.movimientos;

import com.utn.springboot.billeteravirtual.model.cuentas.TipoMoneda;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class Movimiento implements Comparable<Movimiento> {
    @Schema(description = "Identificador único del movimiento", example = "1")
    private final Long id;

    @Schema(description = "Tipo de movimiento", example = "DEPOSITO")
    private final TipoMovimiento tipo;

    @Schema(description = "Monto del movimiento", example = "1000")
    private final Double monto;

    @Schema(description = "Tipo de moneda", example = "ARS")
    private final TipoMoneda tipoMoneda;

    @Schema(description = "Fecha y hora del movimiento", example = "2021-06-01T10:15:30")
    private final LocalDateTime fecha;

    @Schema(description = "Descripción del movimiento", example = "Movimiento de DEPOSITO DE DINERO por $1000")
    private final String descripcion;

    // Constructor
    public Movimiento(Long id, TipoMovimiento tipo, Double monto, TipoMoneda tipoMoneda) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.tipoMoneda = tipoMoneda;
        this.fecha = LocalDateTime.now();
        this.descripcion = "Movimiento de " + tipo.getDescripcion() + " por " + tipoMoneda.getSimbolo() + monto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public int compareTo(Movimiento movimiento) {
        return this.fecha.compareTo(movimiento.getFecha());
    }
}
