package com.utn.springboot.billeteravirtual.repository.entity.security;

import com.utn.springboot.billeteravirtual.types.PermisoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permisos")
@NoArgsConstructor
@Getter
public class PermisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PermisoUsuario nombre;

    @Column(nullable = false, length = 50)
    private String descripcion;

    public PermisoEntity(PermisoUsuario nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
