package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.controller.OrdenUsuario;
import com.utn.springboot.billeteravirtual.exception.UsuarioNoExistenteException;
import com.utn.springboot.billeteravirtual.model.Usuario;
import com.utn.springboot.billeteravirtual.utils.Utilidades;
import com.utn.springboot.billeteravirtual.utils.log.CodigoLog;
import com.utn.springboot.billeteravirtual.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    public List<Usuario> obtenerTodos() {
        return usuarios;
    }

    public Usuario buscarUsuario(Long id) throws UsuarioNoExistenteException {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst().orElseThrow(() -> new UsuarioNoExistenteException(id));
    }

    // Método para buscar usuarios por nombre y rango de edad. Si alguno de los parámetros es null, no se aplicará el filtro.
    // El orden de los usuarios será por ID.
    public List<Usuario> buscarUsuarios(String nombre, Integer edadMin, Integer edadMax) {
        return usuarios.stream()
                .filter(usuario -> nombre == null || usuario.getNombre().toUpperCase().contains(nombre.toUpperCase()))
                .filter(usuario -> edadMin == null || usuario.getEdad() >= edadMin)
                .filter(usuario -> edadMax == null || usuario.getEdad() <= edadMax)
                .sorted(Comparator.comparing(Usuario::getId))
                .toList();
    }

    // Método para buscar usuarios por nombre y rango de edad. Si alguno de los parámetros es null, no se aplicará el filtro.
    // El orden de los usuarios será el especificado en el parámetro orden.
    public List<Usuario> buscarUsuarios(String nombre, Integer edadMin, Integer edadMax, OrdenUsuario orden) {
        return usuarios.stream()
                .filter(usuario -> nombre == null || usuario.getNombre().toUpperCase().contains(nombre.toUpperCase()))
                .filter(usuario -> edadMin == null || usuario.getEdad() >= edadMin)
                .filter(usuario -> edadMax == null || usuario.getEdad() <= edadMax)
                .sorted(orden.getComparator())
                .toList();
    }

    // Método para crear un nuevo usuario.
    // El ID del usuario será el siguiente al último usuario registrado.
    // El nombre del usuario será formateado a mayúsculas y sin espacios al principio y al final.
    // El email del usuario será formateado a minúsculas.
    // El método devolverá el usuario creado.
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setId((long) (usuarios.size() + 1)); // Asignar ID al usuario nuevo
        String nombre = utilidades.formatearTexto(usuario.getNombre());
        usuario.setNombre(nombre);
        usuario.setEmail(usuario.getEmail().toLowerCase());
        usuarios.add(usuario);
        log.registrarAccion(CodigoLog.USUARIO_CREADO, usuario);
        return usuario;
    }

    // Método para actualizar parcialmente un usuario existente. Si el usuario no existe, se lanzará una excepción
    // UsuarioNoEncontradoException.
    // Si un atributo del usuario actualizado es null, no se actualizará.
    // El nombre del usuario será formateado a mayúsculas y sin espacios al principio y al final.
    // El email del usuario será formateado a minúsculas.
    // El método devolverá el usuario actualizado.
    public Usuario actualizarUsuarioParcial(Long id, Usuario usuarioActualizado) throws UsuarioNoExistenteException {
        Usuario usuarioRetorno = usuarios.stream().filter(usuario -> usuario.getId().equals(id)).peek(usuario -> {
            if (usuarioActualizado.getNombre() != null) {
                usuario.setNombre(utilidades.formatearTexto(usuarioActualizado.getNombre()));
            }
            if (usuarioActualizado.getEmail() != null) {
                usuario.setEmail(usuarioActualizado.getEmail().toLowerCase());
            }
            if (usuarioActualizado.getEdad() != null) {
                usuario.setEdad(usuarioActualizado.getEdad());
            }
        }).findFirst().orElseThrow(() -> new UsuarioNoExistenteException(id));

        log.registrarAccion(CodigoLog.USUARIO_ACTUALIZADO, usuarioRetorno);
        return usuarioRetorno;
    }

    // Método para eliminar un usuario por ID. Devuelve true si el usuario fue eliminado, false en caso contrario.
    public boolean eliminarUsuario(Long id) {
        boolean resultado = usuarios.removeIf(usuario -> usuario.getId().equals(id));
        if (resultado) log.registrarAccion(CodigoLog.USUARIO_ELIMINADO, id);
        return resultado;
    }
}
