package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.transacciones.PagoServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoServicioRepository extends JpaRepository<PagoServicioEntity, Long> {
}
