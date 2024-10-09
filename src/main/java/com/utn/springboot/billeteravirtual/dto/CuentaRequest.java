package com.utn.springboot.billeteravirtual.dto;

import com.utn.springboot.billeteravirtual.types.TipoCuenta;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Payload para la creación de una cuenta")
public class CuentaRequest {
    @Schema(description = "Tipo de cuenta", example = "AHORROS", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private TipoCuenta tipoCuenta;

    @Schema(description = "Identificador único del usuario", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long idUsuario;

    @Schema(description = "Balance actual de la cuenta", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BigDecimal balance;

    @Schema(description = "Tipo de moneda", example = "ARS", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private TipoMoneda tipoMoneda;

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }
}
