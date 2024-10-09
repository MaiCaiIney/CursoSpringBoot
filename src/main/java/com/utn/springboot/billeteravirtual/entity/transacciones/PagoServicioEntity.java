package com.utn.springboot.billeteravirtual.entity.transacciones;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pagos_servicios")
public class PagoServicioEntity extends TransaccionEntity {
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    public PagoServicioEntity() {
    }

    public PagoServicioEntity(BigDecimal monto, CuentaEntity cuenta, ServicioEntity servicio) {
        super(TipoTransaccion.PAGO_SERVICIO, monto, cuenta);
        this.servicio = servicio;
    }

    public ServicioEntity getServicio() {
        return servicio;
    }
}
