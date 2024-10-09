package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    CuentaEntity findByAliasOrCbu(String alias, String cbu);

    List<CuentaEntity> findByUsuarioId(Long id);
}
