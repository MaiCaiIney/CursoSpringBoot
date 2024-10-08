package com.utn.springboot.billeteravirtual.model;

import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final List<Cuenta> cuentas = new ArrayList<>();
    private Long id;
    private String nombre;
    private String email;
    private int edad;
    private String domicilio;

    public Usuario(String nombre, String email, int edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    @Override
    public String toString() {
        return """
                {
                    "id": %d,
                    "nombre": "%s",
                    "email": "%s",
                    "edad": %d
                }
                """.formatted(id, nombre, email, edad);
    }
}

