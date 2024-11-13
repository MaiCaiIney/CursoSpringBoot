package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.repository.entity.security.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
}
