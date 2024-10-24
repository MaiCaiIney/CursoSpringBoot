package com.utn.springboot.billeteravirtual.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notificaciones")
public class NotificacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @ManyToMany
    @JoinTable(
            name = "usuario_notificaciones",
            joinColumns = @JoinColumn(name = "notificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<UsuarioEntity> usuarios;
}
