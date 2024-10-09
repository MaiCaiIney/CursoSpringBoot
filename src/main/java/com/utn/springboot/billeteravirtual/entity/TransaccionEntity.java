package com.utn.springboot.billeteravirtual.entity;

import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transacciones")
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final TipoTransaccion tipoTransaccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private final BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private final CuentaEntity cuenta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaTransaccion;

    public TransaccionEntity(TipoTransaccion tipoTransaccion, BigDecimal monto, CuentaEntity cuenta) {
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.cuenta = cuenta;
    }

    public Long getId() {
        return id;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public CuentaEntity getCuenta() {
        return cuenta;
    }

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }
}
