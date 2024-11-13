package com.utn.springboot.billeteravirtual.repository.entity.security;

import com.utn.springboot.billeteravirtual.types.PerfilUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfiles")
@NoArgsConstructor
@Getter
public class PerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PerfilUsuario nombre;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "perfil_roles",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private final Set<RolEntity> roles = new HashSet<>();

    public PerfilEntity(PerfilUsuario nombre) {
        this.nombre = nombre;
    }

    public void agregarRol(RolEntity rol) {
        this.roles.add(rol);
    }
}
