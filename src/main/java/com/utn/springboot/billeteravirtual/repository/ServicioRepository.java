package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.transacciones.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {
}
