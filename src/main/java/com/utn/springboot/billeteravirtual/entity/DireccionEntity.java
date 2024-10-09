package com.utn.springboot.billeteravirtual.entity;

import com.utn.springboot.billeteravirtual.types.TipoDireccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "direcciones")
public class DireccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "direccion")
    private UsuarioEntity usuario;

    @NotBlank
    private String calle;

    private String numero;

    private String otro;

    @NotBlank
    @Column(nullable = false)
    private String localidad;

    @NotBlank
    @Column(nullable = false)
    private String provincia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDireccion tipo;

    public DireccionEntity() {
    }

    public DireccionEntity(String calle, String numero, String otro, String localidad, String provincia, TipoDireccion tipo) {
        this.calle = calle;
        this.numero = numero;
        this.otro = otro;
        this.localidad = localidad;
        this.provincia = provincia;
        this.tipo = tipo;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getOtro() {
        return otro;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public TipoDireccion getTipo() {
        return tipo;
    }
}
