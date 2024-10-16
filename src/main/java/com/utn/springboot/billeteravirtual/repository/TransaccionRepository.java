package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.entity.projections.TransaccionProjection;
import com.utn.springboot.billeteravirtual.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {
    List<TransaccionProjection> findByCuenta(CuentaEntity cuenta);

    @Query("SELECT new com.utn.springboot.billeteravirtual.model.cuentas.Transaccion(t.tipoTransaccion, t.monto, t.fechaTransaccion) FROM" +
            " TransaccionEntity t WHERE t.cuenta.id = :cuentaId")
    List<Transaccion> findByCuentaId(@Param("cuentaId") Long cuentaId);
}
