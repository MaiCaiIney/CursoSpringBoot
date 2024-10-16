package com.utn.springboot.billeteravirtual.entity.transacciones;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("PAGO_SERVICIO")
@Table(name = "pagos_servicios")
public class PagoServicioEntity extends TransaccionEntity {
    @ManyToOne
    @JoinColumn(name = "servicio_id")
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
