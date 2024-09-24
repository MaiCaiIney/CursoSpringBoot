package com.utn.springboot.billeteravirtual.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Clase que representa un usuario")
public class Usuario {
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;
    // @JsonProperty("nombreCompleto") es un ejemplo de cómo se puede cambiar el nombre de un atributo en el JSON.
    @Schema(description = "Nombre completo del usuario", example = "Mai Prueba")
    @NotNull
    @Size(min = 3, max = 50, message = "El nombre del usuario debe tener entre 3 y 50 caracteres")
    private String nombre;
    @Schema(description = "Correo electrónico del usuario", example = "mai@example.com")
    @Email
    private String email;
    @Schema(description = "Edad del usuario", example = "34")
    @Min(18)
    @Max(99)
    private int edad;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(Long id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}

