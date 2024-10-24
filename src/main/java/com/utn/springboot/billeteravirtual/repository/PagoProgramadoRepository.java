package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.repository.entity.transacciones.PagoProgramadoEntity;
import com.utn.springboot.billeteravirtual.types.EstadoPagoProgramado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoProgramadoRepository extends JpaRepository<PagoProgramadoEntity, Long> {
    List<PagoProgramadoEntity> findByEstado(EstadoPagoProgramado estado);

    List<PagoProgramadoEntity> findByEstadoIn(List<EstadoPagoProgramado> estados);

    List<PagoProgramadoEntity> findByEstadoAndProximaEjecucionBefore(EstadoPagoProgramado estadoPagoProgramado, LocalDateTime fecha);
}
