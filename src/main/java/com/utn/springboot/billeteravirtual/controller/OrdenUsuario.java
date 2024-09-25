package com.utn.springboot.billeteravirtual.controller;

import com.utn.springboot.billeteravirtual.model.Usuario;

import java.util.Comparator;

public enum OrdenUsuario {
    ID, NOMBRE, EMAIL;

    public Comparator<Usuario> getComparator() {
        return switch (this) {
            case ID -> Comparator.comparing(Usuario::getId);
            case NOMBRE -> Comparator.comparing(Usuario::getNombre);
            case EMAIL -> Comparator.comparing(Usuario::getEmail);
        };
    }
}