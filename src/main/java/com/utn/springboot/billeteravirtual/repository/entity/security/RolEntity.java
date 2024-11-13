package com.utn.springboot.billeteravirtual.repository.entity.security;

import com.utn.springboot.billeteravirtual.types.RolUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RolUsuario nombre;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "rol_permisos",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private final Set<PermisoEntity> permisos = new HashSet<>();

    public RolEntity(RolUsuario nombre) {
        this.nombre = nombre;
    }

    public void agregarPermiso(PermisoEntity permiso) {
        this.permisos.add(permiso);
    }
}
