package com.utn.springboot.billeteravirtual.controller.dto;

import com.utn.springboot.billeteravirtual.types.FrecuenciaPagoProgramado;
import com.utn.springboot.billeteravirtual.types.TipoPagoProgramado;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoProgramadoRequest(
        TipoPagoProgramado tipoPagoProgramado,
        BigDecimal monto,
        LocalDateTime fechaInicio,
        FrecuenciaPagoProgramado periodicidad,
        Long idCuentaOrigen,
        Long idCuentaDestino
) {
}
