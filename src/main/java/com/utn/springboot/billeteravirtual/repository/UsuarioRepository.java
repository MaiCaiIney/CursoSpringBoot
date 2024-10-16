package com.utn.springboot.billeteravirtual.repository;

import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, UsuarioRepositoryCustom {

    // Método para encontrar usuarios por nombre, ignorando mayúsculas o minúsculas
    Page<UsuarioEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Método para encontrar usuarios dentro de un rango de edad
    Page<UsuarioEntity> findByEdadGreaterThanEqualAndEdadLessThanEqual(Integer edadMin, Integer edadMax, Pageable pageable);

    // Método para encontrar usuarios por nombre y dentro de un rango de edad
    Page<UsuarioEntity> findByNombreContainingIgnoreCaseAndEdadGreaterThanEqualAndEdadLessThanEqual(String nombre, Integer edadMin,
                                                                                                    Integer edadMax, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE " +
            "(:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:edadMin IS NULL OR u.edad >= :edadMin) " +
            "AND (:edadMax IS NULL OR u.edad <= :edadMax)")
    List<UsuarioEntity> findAllByNameAndEdadBetween(@Param("nombre") String nombre,
                                                    @Param("edadMin") Integer edadMin,
                                                    @Param("edadMax") Integer edadMax);

    @Query(value = "SELECT * FROM usuarios u WHERE " +
            "(:nombre IS NULL OR LOWER(u.nombre_completo) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:edadMin IS NULL OR u.edad >= :edadMin) " +
            "AND (:edadMax IS NULL OR u.edad <= :edadMax)",
            nativeQuery = true)
    List<UsuarioEntity> findAllByNameAndEdadBetweenSQL(@Param("nombre") String nombre,
                                                       @Param("edadMin") Integer edadMin,
                                                       @Param("edadMax") Integer edadMax);

    @EntityGraph(value = "Usuario.cuentas", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM UsuarioEntity u")
    List<UsuarioEntity> findAllUsersAndAccountsEntityGraph();

    @Query("SELECT u FROM UsuarioEntity u LEFT JOIN FETCH u.cuentas")
    List<UsuarioEntity> findAllUsersAndAccounts();

}
