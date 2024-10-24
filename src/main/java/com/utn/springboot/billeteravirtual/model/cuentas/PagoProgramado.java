package com.utn.springboot.billeteravirtual.model.cuentas;

import com.utn.springboot.billeteravirtual.types.FrecuenciaPagoProgramado;
import com.utn.springboot.billeteravirtual.types.TipoPagoProgramado;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoProgramado {
    private Long id;
    private TipoPagoProgramado tipoPagoProgramado;
    private BigDecimal monto;
    private LocalDateTime fechaInicio;
    private FrecuenciaPagoProgramado periodicidad;
    private LocalDateTime fechaUltimaEjecucion;
    private LocalDateTime fechaProximaEjecucion;
    private Long idCuentaOrigen;
    private Long idCuentaDestino;

    public Long getId() {
        return id;
    }

    public TipoPagoProgramado getTipoPagoProgramado() {
        return tipoPagoProgramado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public FrecuenciaPagoProgramado getPeriodicidad() {
        return periodicidad;
    }

    public LocalDateTime getFechaUltimaEjecucion() {
        return fechaUltimaEjecucion;
    }

    public LocalDateTime getFechaProximaEjecucion() {
        return fechaProximaEjecucion;
    }

    public Long getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public Long getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public static class Builder {
        private final PagoProgramado pagoProgramado;

        public Builder() {
            pagoProgramado = new PagoProgramado();
        }

        public Builder setId(Long id) {
            pagoProgramado.id = id;
            return this;
        }

        public Builder setTipoPago(TipoPagoProgramado tipoPagoProgramado) {
            pagoProgramado.tipoPagoProgramado = tipoPagoProgramado;
            return this;
        }

        public Builder setMonto(BigDecimal monto) {
            pagoProgramado.monto = monto;
            return this;
        }

        public Builder setFechaInicio(LocalDateTime fechaInicio) {
            pagoProgramado.fechaInicio = fechaInicio;
            return this;
        }

        public Builder setPeriodicidad(FrecuenciaPagoProgramado periodicidad) {
            pagoProgramado.periodicidad = periodicidad;
            return this;
        }

        public Builder setFechaUltimaEjecucion(LocalDateTime fechaUltimaEjecucion) {
            pagoProgramado.fechaUltimaEjecucion = fechaUltimaEjecucion;
            return this;
        }

        public Builder setFechaProximaEjecucion(LocalDateTime fechaProximaEjecucion) {
            pagoProgramado.fechaProximaEjecucion = fechaProximaEjecucion;
            return this;
        }

        public Builder setIdCuentaOrigen(Long idCuentaOrigen) {
            pagoProgramado.idCuentaOrigen = idCuentaOrigen;
            return this;
        }

        public Builder setIdCuentaDestino(Long idCuentaDestino) {
            pagoProgramado.idCuentaDestino = idCuentaDestino;
            return this;
        }

        public PagoProgramado build() {
            return pagoProgramado;
        }
    }
}
