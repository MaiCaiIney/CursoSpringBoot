package com.utn.springboot.billeteravirtual.repository.entity.transacciones;

import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "discriminador", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("TRANSACCION")
@Table(name = "transacciones")
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipoTransaccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaEntity cuenta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaTransaccion;

    public TransaccionEntity() {
    }

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
