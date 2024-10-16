package com.utn.springboot.billeteravirtual.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    protected U creadoPor;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime fechaCreacion;

    @LastModifiedBy
    protected U modificadoPor;

    @LastModifiedDate
    protected LocalDateTime fechaModificacion;
}

