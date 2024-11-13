package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.repository.entity.security.CredencialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialRepository extends JpaRepository<CredencialEntity, Long> {
    Optional<CredencialEntity> findByUser(String user);
}
