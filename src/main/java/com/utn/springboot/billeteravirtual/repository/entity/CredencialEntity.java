package com.utn.springboot.billeteravirtual.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "credenciales")
public class CredencialEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "nombre_usuario", nullable = false, length = 20)
    private String user;

    @NonNull
    @Column(name = "password", nullable = false)
    private String pass;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true)
    private UsuarioEntity usuario;
}
