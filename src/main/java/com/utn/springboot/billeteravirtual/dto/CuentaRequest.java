package com.utn.springboot.billeteravirtual.dto;

import com.utn.springboot.billeteravirtual.model.cuentas.TipoCuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.TipoMoneda;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

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
    private Double balance;

    @Schema(description = "Tipo de moneda", example = "ARS", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private TipoMoneda tipoMoneda;

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Double getBalance() {
        return balance;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }
}
