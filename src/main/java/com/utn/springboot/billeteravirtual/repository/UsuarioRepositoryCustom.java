package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioRepositoryCustom {
    List<UsuarioEntity> buscarUsuariosConFiltrosAPICriteria(String nombre, Integer edadMin, Integer edadMax);
}
