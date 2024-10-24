package com.utn.springboot.billeteravirtual.repository.entity.projections;

import com.utn.springboot.billeteravirtual.types.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransaccionProjection {
    TipoTransaccion getTipoTransaccion();

    BigDecimal getMonto();

    LocalDateTime getFechaTransaccion();
}
