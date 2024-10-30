package com.utn.springboot.billeteravirtual.repository.entity;

import com.utn.springboot.billeteravirtual.repository.entity.transacciones.PagoProgramadoEntity;
import com.utn.springboot.billeteravirtual.types.EstadoUsuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios", indexes = {
        @Index(name = "idx_usuario_email", columnList = "email")
})
@NamedEntityGraph(
        name = "Usuario.cuentas",
        attributeNodes = @NamedAttributeNode("cuentas")
)
public class UsuarioEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 50)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_usuario")
    private EstadoUsuario estado;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id", unique = true)
    private DireccionEntity direccion;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<CuentaEntity> cuentas;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<NotificacionEntity> notificaciones;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<PagoProgramadoEntity> pagosProgramados;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long id) {
        this.id = id;
    }

    public UsuarioEntity(String nombre, String email, Integer edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    @PrePersist
    public void prePersist() {
        if (estado == null) {
            this.estado = EstadoUsuario.ACTIVO;
        }
    }

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

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public DireccionEntity getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionEntity direccion) {
        this.direccion = direccion;
    }

    public List<CuentaEntity> getCuentas() {
        return cuentas;
    }

    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public List<PagoProgramadoEntity> getPagosProgramados() {
        return pagosProgramados;
    }
}
