package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.controller.OrdenUsuario;
import com.utn.springboot.billeteravirtual.entity.DireccionEntity;
import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.exception.UsuarioNoExistenteException;
import com.utn.springboot.billeteravirtual.model.Usuario;
import com.utn.springboot.billeteravirtual.repository.UsuarioRepository;
import com.utn.springboot.billeteravirtual.utils.Utilidades;
import com.utn.springboot.billeteravirtual.utils.log.CodigoLog;
import com.utn.springboot.billeteravirtual.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Esta clase deberá tener la lógica de negocio para poder realizar las operaciones CRUD de la entidad Usuario.
// La anotación @Service indica que esta clase es un servicio. En Spring, los servicios son clases que contienen la lógica de negocio.
@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final Utilidades utilidades;
    private final Log log;

    @Autowired
    public UsuarioService(UsuarioRepository repository, Utilidades utilidades, @Qualifier("consoleLog") Log logService) {
        this.repository = repository;
        this.utilidades = utilidades;
        this.log = logService;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return repository.findAll().stream().map(this::convertirEntityAUsuario).collect(Collectors.toList());
    }

    public Usuario buscarUsuario(Long id) throws UsuarioNoExistenteException {
        return repository.findById(id).map(this::convertirEntityAUsuario).orElseThrow(() -> new UsuarioNoExistenteException(id));
    }

    // Método para buscar usuarios por nombre y rango de edad. Si alguno de los parámetros es null, no se aplicará el filtro.
    // El orden de los usuarios será el especificado en el parámetro orden.
    public List<Usuario> buscarUsuarios(String nombre, Integer edadMin, Integer edadMax, OrdenUsuario orden) {
        List<UsuarioEntity> entidades;
//        if (nombre != null && edadMin != null && edadMax != null) {
//            entidades = repository.findByNombreContainingIgnoreCaseAndEdadGreaterThanEqualAndEdadLessThanEqual(nombre, edadMin, edadMax);
//        } else if (nombre != null) {
//            entidades = repository.findByNombreContainingIgnoreCase(nombre);
//        } else if (edadMin != null && edadMax != null) {
//            entidades = repository.findByEdadGreaterThanEqualAndEdadLessThanEqual(edadMin, edadMax);
//        } else {
//            entidades = repository.findAll();
//        }

//        entidades = repository.findByNombreContainingIgnoreCaseAndEdadGreaterThanEqualAndEdadLessThanEqual(nombre, edadMin, edadMax);
//        entidades = repository.buscarUsuariosConFiltros(nombre, edadMin, edadMax);
//        entidades = repository.buscarUsuariosConFiltrosNativo(nombre, edadMin, edadMax);
        entidades = repository.buscarUsuariosConFiltrosAPICriteria(nombre, edadMin, edadMax);

        return entidades.stream().map(this::convertirEntityAUsuario)
                .sorted(orden.getComparator())
                .collect(Collectors.toList());
    }

    // Método para crear un nuevo usuario.
    // El ID del usuario será el siguiente al último usuario registrado.
    // El nombre del usuario será formateado a mayúsculas y sin espacios al principio y al final.
    // El email del usuario será formateado a minúsculas.
    // El método devolverá el usuario creado.
    public Usuario crearUsuario(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(utilidades.formatearTexto(usuario.getNombre()));
        entity.setEmail(usuario.getEmail().toLowerCase());
        entity.setEdad(usuario.getEdad());
        entity = repository.save(entity);
        log.registrarAccion(CodigoLog.USUARIO_CREADO, entity);
        return convertirEntityAUsuario(entity);
    }

    // Método para actualizar parcialmente un usuario existente. Si el usuario no existe, se lanzará una excepción
    // UsuarioNoEncontradoException.
    // Si un atributo del usuario actualizado es null, no se actualizará.
    // El nombre del usuario será formateado a mayúsculas y sin espacios al principio y al final.
    // El email del usuario será formateado a minúsculas.
    // El método devolverá el usuario actualizado.
    public Usuario actualizarUsuarioParcial(Long id, Usuario usuarioActualizado) throws UsuarioNoExistenteException {
        UsuarioEntity entity = repository.findById(id)
                .orElseThrow(() -> new UsuarioNoExistenteException(id));

        if (usuarioActualizado.getNombre() != null) {
            entity.setNombre(utilidades.formatearTexto(usuarioActualizado.getNombre()));
        }
        if (usuarioActualizado.getEmail() != null) {
            entity.setEmail(usuarioActualizado.getEmail().toLowerCase());
        }
        if (usuarioActualizado.getEdad() != null) {
            entity.setEdad(usuarioActualizado.getEdad());
        }

        entity = repository.save(entity);
        log.registrarAccion(CodigoLog.USUARIO_ACTUALIZADO, entity);
        return convertirEntityAUsuario(entity);
    }

    // Método para eliminar un usuario por ID. Devuelve true si el usuario fue eliminado, false en caso contrario.
    public boolean eliminarUsuario(Long id) {
        boolean existe = repository.existsById(id);
        repository.deleteById(id);
        if (existe) log.registrarAccion(CodigoLog.USUARIO_ELIMINADO, id);
        return existe;
    }

    private Usuario convertirEntityAUsuario(UsuarioEntity usuarioEntity) {
        Usuario usuario = new Usuario(usuarioEntity.getId(), usuarioEntity.getNombre(), usuarioEntity.getEmail(), usuarioEntity.getEdad());
        DireccionEntity direccion = usuarioEntity.getDireccion();
        if (direccion != null) {
            String domicilio = "%s %s %s, %s".formatted(direccion.getCalle(), direccion.getNumero(), direccion.getOtro(),
                                                        direccion.getLocalidad());
            usuario.setDomicilio(domicilio);
        }
        return usuario;
    }
}
