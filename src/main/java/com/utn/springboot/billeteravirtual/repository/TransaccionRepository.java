package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {
}
