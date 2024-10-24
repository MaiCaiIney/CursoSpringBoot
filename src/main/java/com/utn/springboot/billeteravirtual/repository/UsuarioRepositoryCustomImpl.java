package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UsuarioEntity> buscarUsuariosConFiltrosAPICriteria(String nombre, Integer edadMin, Integer edadMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntity> query = cb.createQuery(UsuarioEntity.class);
        Root<UsuarioEntity> usuario = query.from(UsuarioEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null) {
            predicates.add(cb.like(cb.lower(usuario.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        if (edadMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(usuario.get("edad"), edadMin));
        }
        if (edadMax != null) {
            predicates.add(cb.lessThanOrEqualTo(usuario.get("edad"), edadMax));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
