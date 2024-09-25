package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.exception.UsuarioNoEncontradoException;
import com.utn.springboot.billeteravirtual.model.Usuario;
import com.utn.springboot.billeteravirtual.utils.Utilidades;
import com.utn.springboot.billeteravirtual.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Esta clase deberá tener la lógica de negocio para poder realizar las operaciones CRUD de la entidad Usuario.
// La anotación @Service indica que esta clase es un servicio. En Spring, los servicios son clases que contienen la lógica de negocio.
@Service
public class UsuarioService {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final Utilidades utilidades;
    private final Log log;

    @Autowired
    public UsuarioService(Utilidades utilidades, @Qualifier("consoleLog") Log logService) {
        this.utilidades = utilidades;
        this.log = logService;
        usuarios.add(new Usuario(1L, "Juan Perez", "juan@example.com", 41));
        usuarios.add(new Usuario(2L, "Ana Garcia", "ana@example.com", 34));
        usuarios.add(new Usuario(3L, "Maria Romero", "maria@example.com", 27));
        usuarios.add(new Usuario(4L, "Roberto Aguirre", "roberto@example.com", 60));
        usuarios.add(new Usuario(5L, "Jose Ortiz", "jose@example.com", 23));
        usuarios.add(new Usuario(6L, "Patricia Barreto", "maria@example.com", 27));
        usuarios.add(new Usuario(7L, "Juan Pablo Villa", "jpv@example.com", 44));
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarios;
    }

    // Método para crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setId((long) (usuarios.size() + 1)); // Asignar ID al usuario nuevo
        String nombre = utilidades.formatearTexto(usuario.getNombre());
        usuario.setNombre(nombre);
        usuarios.add(usuario);
        return usuario;
    }

    // Método para actualizar un usuario existente. Si el usuario no existe, se lanzará una excepción UsuarioNoEncontradoException.
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) throws UsuarioNoEncontradoException {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).peek(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setEdad(usuarioActualizado.getEdad());
        }).findFirst().orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    // Método para eliminar un usuario por ID. Devuelve true si el usuario fue eliminado, false en caso contrario.
    public boolean eliminarUsuario(Long id) {
        return usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }
}
