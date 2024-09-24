package com.utn.springboot.billeteravirtual.handler;

import com.utn.springboot.billeteravirtual.exception.UsuarioNoEncontradoException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// @ControllerAdvice es una anotación de Spring que permite definir un controlador global para manejar excepciones en toda la aplicación.
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler indica que este método maneja excepciones de tipo UsuarioNoEncontradoException.
    // Si se lanza una excepción de este tipo, el método recibe un objeto UsuarioNoEncontradoException y devuelve un ResponseEntity con
    // el mensaje de error y el código de estado HTTP 404 (NOT_FOUND).
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"id\": 1 }")))
    })
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Long>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        Map<String, Long> errores = new HashMap<>();
        errores.put("id", ex.getId());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    // @ExceptionHandler indica que este método maneja excepciones de tipo MethodArgumentNotValidException.
    // Este método maneja las excepciones de validación de los campos de un objeto que se envía en una petición POST o PUT.
    // El método recibe un objeto MethodArgumentNotValidException que contiene los errores de validación.
    // El método devuelve un ResponseEntity con un mapa que contiene los errores de validación y el código de estado HTTP 400 (BAD_REQUEST).
    // El mapa tiene el nombre del campo y el mensaje de error.
    // Por ejemplo, si se envía un objeto Usuario con el campo nombre vacío, el mapa contendrá el campo "nombre" y el mensaje "no puede
    // estar vacío".
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }")))
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // Se recorren los errores de validación y se guardan en un mapa con el nombre del campo y el mensaje de error.
        ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
