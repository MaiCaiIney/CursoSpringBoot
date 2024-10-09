package com.utn.springboot.billeteravirtual.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class ServicioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreServicio;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false)
    private String cuit;

    public ServicioEntity(String nombreServicio, String empresa, String cuit) {
        this.nombreServicio = nombreServicio;
        this.empresa = empresa;
        this.cuit = cuit;
    }
}
