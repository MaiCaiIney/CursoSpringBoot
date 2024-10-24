package com.utn.springboot.billeteravirtual.repository.entity.transacciones;

import com.utn.springboot.billeteravirtual.repository.entity.Auditable;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.types.EstadoPagoProgramado;
import com.utn.springboot.billeteravirtual.types.FrecuenciaPagoProgramado;
import com.utn.springboot.billeteravirtual.types.TipoPagoProgramado;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos_programados")
public class PagoProgramadoEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    private CuentaEntity cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id", nullable = false)
    private CuentaEntity cuentaDestino;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPagoProgramado tipoPago;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FrecuenciaPagoProgramado frecuencia;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
//    @FutureOrPresent(message = "La fecha de inicio debe ser actual o futura")
    private LocalDateTime fechaInicio;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoPagoProgramado estado;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimaEjecucion;

    private String logEjecucion;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
//    @FutureOrPresent(message = "La próxima ejecución debe ser futura")
    private LocalDateTime proximaEjecucion;

    public PagoProgramadoEntity() {
    }

    public PagoProgramadoEntity(PagoProgramadoEntity original) {
        this.usuario = original.usuario;
        this.cuentaOrigen = original.cuentaOrigen;
        this.cuentaDestino = original.cuentaDestino;
        this.tipoPago = original.tipoPago;
        this.monto = original.monto;
        this.frecuencia = original.frecuencia;
        this.fechaInicio = original.fechaInicio;
        this.proximaEjecucion = original.proximaEjecucion;
    }

    public Long getId() {
        return id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public CuentaEntity getCuentaOrigen() {
        return cuentaOrigen;
    }

    public CuentaEntity getCuentaDestino() {
        return cuentaDestino;
    }

    public TipoPagoProgramado getTipoPago() {
        return tipoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public FrecuenciaPagoProgramado getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaPagoProgramado frecuencia) {
        this.frecuencia = frecuencia;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public EstadoPagoProgramado getEstado() {
        return estado;
    }

    public void setEstado(EstadoPagoProgramado estado) {
        this.estado = estado;
    }

    public LocalDateTime getUltimaEjecucion() {
        return ultimaEjecucion;
    }

    public void setUltimaEjecucion(LocalDateTime ultimaEjecucion) {
        this.ultimaEjecucion = ultimaEjecucion;
    }

    public String getLogEjecucion() {
        return logEjecucion;
    }

    public void setLogEjecucion(String logEjecucion) {
        this.logEjecucion = logEjecucion;
    }

    public LocalDateTime getProximaEjecucion() {
        return proximaEjecucion;
    }

    public void setProximaEjecucion(LocalDateTime proximaEjecucion) {
        this.proximaEjecucion = proximaEjecucion;
    }

    public static class Builder {
        private final PagoProgramadoEntity pago;

        public Builder() {
            this.pago = new PagoProgramadoEntity();
        }

        public Builder setUsuario(UsuarioEntity usuario) {
            this.pago.usuario = usuario;
            return this;
        }

        public Builder setCuentaOrigen(CuentaEntity cuentaOrigen) {
            this.pago.cuentaOrigen = cuentaOrigen;
            return this;
        }

        public Builder setCuentaDestino(CuentaEntity cuentaDestino) {
            this.pago.cuentaDestino = cuentaDestino;
            return this;
        }

        public Builder setTipoPago(TipoPagoProgramado tipoPago) {
            this.pago.tipoPago = tipoPago;
            return this;
        }

        public Builder setMonto(BigDecimal monto) {
            this.pago.monto = monto;
            return this;
        }

        public Builder setFrecuencia(FrecuenciaPagoProgramado frecuencia) {
            this.pago.frecuencia = frecuencia;
            return this;
        }

        public Builder setFechaInicio(LocalDateTime fechaInicio) {
            this.pago.fechaInicio = fechaInicio;
            return this;
        }

        public Builder setEstado(EstadoPagoProgramado estado) {
            this.pago.estado = estado;
            return this;
        }

        public Builder setUltimaEjecucion(LocalDateTime ultimaEjecucion) {
            this.pago.ultimaEjecucion = ultimaEjecucion;
            return this;
        }

        public Builder setLogEjecucion(String logEjecucion) {
            this.pago.logEjecucion = logEjecucion;
            return this;
        }

        public Builder setProximaEjecucion(LocalDateTime proximaEjecucion) {
            this.pago.proximaEjecucion = proximaEjecucion;
            return this;
        }

        public PagoProgramadoEntity build() {
            return this.pago;
        }
    }
}
