package com.utn.springboot.billeteravirtual.initializer;

import com.utn.springboot.billeteravirtual.entity.EstadoUsuario;
import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        UsuarioEntity usuario1 = new UsuarioEntity(null, "Juan Perez", "juan.perez@example.com", 35, EstadoUsuario.ACTIVO, LocalDateTime.now());
        UsuarioEntity usuario2 = new UsuarioEntity(null, "Ana Garcia", "ana.garcia@example.com", 22, EstadoUsuario.ACTIVO, LocalDateTime.now());
        UsuarioEntity usuario3 = new UsuarioEntity(null, "Jose Morales", "jose.morales@example.com", 45, EstadoUsuario.INACTIVO, LocalDateTime.now());

//        UsuarioEntity usuario1 = new UsuarioEntity("Juan Perez", "juan.perez@example.com", 35);
//        UsuarioEntity usuario2 = new UsuarioEntity("Ana Garcia", "ana.garcia@example.com", 22);
//        UsuarioEntity usuario3 = new UsuarioEntity("Jose Morales", "jose.morales@example.com", 45);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
    }
}
