package com.utn.springboot.billeteravirtual.initializer;

import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.entity.direccion.DireccionEntity;
import com.utn.springboot.billeteravirtual.entity.direccion.TipoDireccion;
import com.utn.springboot.billeteravirtual.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        UsuarioEntity usuario1 = new UsuarioEntity("Juan Perez", "juan.perez@example.com", 35);
        UsuarioEntity usuario2 = new UsuarioEntity("Ana Garcia", "ana.garcia@example.com", 22);
        UsuarioEntity usuario3 = new UsuarioEntity("Jose Morales", "jose.morales@example.com", 45);

        DireccionEntity direccion1 = new DireccionEntity("Calle 1", "123", "PB C", "Mar del Plata", "Buenos Aires", TipoDireccion.CASA);
        usuario1.setDireccion(direccion1);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
    }
}
