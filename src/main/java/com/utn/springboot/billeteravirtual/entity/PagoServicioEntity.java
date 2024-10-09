package com.utn.springboot.billeteravirtual.entity;

import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "pagos_servicios")
public class PagoServicioEntity extends TransaccionEntity {
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    public PagoServicioEntity(BigDecimal monto, CuentaEntity cuenta, ServicioEntity servicio) {
        super(TipoTransaccion.PAGO_SERVICIO, monto, cuenta);
        this.servicio = servicio;
    }
}
