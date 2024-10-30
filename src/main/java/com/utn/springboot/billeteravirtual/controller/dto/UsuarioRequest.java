package com.utn.springboot.billeteravirtual.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Schema(description = "Payload para la creación de un usuario")
public class UsuarioRequest {
    @Schema(description = "Nombre completo del usuario", example = "Mai Prueba")
    @NotNull
    @Size(min = 3, max = 50, message = "El nombre del usuario debe tener entre 3 y 50 caracteres")
    private String nombre;
    @Schema(description = "Correo electrónico del usuario", example = "mai@example.com")
    @NotNull
    @Email
    private String email;
    @Schema(description = "Edad del usuario", example = "34")
    @NotNull
    @Min(18)
    @Max(99)
    private int edad;

}
