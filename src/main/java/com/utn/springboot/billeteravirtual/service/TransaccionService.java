package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import com.utn.springboot.billeteravirtual.model.mapper.CuentaMapper;
import com.utn.springboot.billeteravirtual.repository.TransaccionRepository;
import com.utn.springboot.billeteravirtual.repository.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.repository.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;

    @Autowired
    @Lazy
    private CuentaService cuentaService;

    @Autowired
    @Lazy
    private CuentaMapper cuentaMapper;

    @Autowired
    public TransaccionService(TransaccionRepository repository) {
        this.transaccionRepository = repository;
    }

    public List<Transaccion> obtenerTransaccionesPorCuenta(Long idCuenta) {
//        CuentaEntity cuentaEntity = cuentaService.buscarEntidadPorId(idCuenta);
//        return transaccionRepository.findByCuenta(cuentaEntity).stream().map(cuentaMapper::toModel).toList();
        return transaccionRepository.findByCuentaId(idCuenta);
    }

    public Transaccion registrarTransaccion(Long id, TipoTransaccion tipoTransaccion, BigDecimal monto) {
        CuentaEntity cuentaEntity = cuentaService.buscarEntidadPorId(id);

        TransaccionEntity transaccionEntity = new TransaccionEntity(tipoTransaccion, monto, cuentaEntity);
        transaccionEntity = transaccionRepository.save(transaccionEntity);
        return cuentaMapper.toModel(transaccionEntity);
    }
}
