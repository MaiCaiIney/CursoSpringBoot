package com.utn.springboot.billeteravirtual.entity.transacciones;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("TRANSFERENCIA")
@Table(name = "transferencias")
public class TransferenciaEntity extends TransaccionEntity {
    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private CuentaEntity cuentaDestino;

    public TransferenciaEntity() {
    }

    public TransferenciaEntity(BigDecimal monto, CuentaEntity cuentaOrigen, CuentaEntity cuentaDestino) {
        super(TipoTransaccion.TRANSFERENCIA, monto, cuentaOrigen);
        this.cuentaDestino = cuentaDestino;
    }

    public CuentaEntity getCuentaDestino() {
        return cuentaDestino;
    }
}
