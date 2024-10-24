package com.utn.springboot.billeteravirtual.exception.handler;

import com.utn.springboot.billeteravirtual.exception.*;
import com.utn.springboot.billeteravirtual.utils.log.CodigoLog;
import com.utn.springboot.billeteravirtual.utils.log.Log;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final Log log;

    @Autowired
    public GlobalExceptionHandler(@Qualifier("fileLog") Log log) {
        this.log = log;
    }

    // @ExceptionHandler indica que este método maneja excepciones de tipo UsuarioNoEncontradoException.
    // Si se lanza una excepción de este tipo, el método recibe un objeto UsuarioNoEncontradoException y devuelve un ResponseEntity con
    // el mensaje de error y el código de estado HTTP 404 (NOT_FOUND).
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"id\": 1, \"error\": \"Usuario no encontrado\" }"))
            )
    })
    @ExceptionHandler(UsuarioNoExistenteException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontrado(UsuarioNoExistenteException ex) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("id", ex.getId());
        errores.put("error", ex.getMessage());
        log.registrarAccion(CodigoLog.USUARIO_NO_ENCONTRADO, ex.getId());
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
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }")))
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // Se recorren los errores de validación y se guardan en un mapa con el nombre del campo y el mensaje de error.
        ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Cuenta no encontrada",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"id\": 1, \"idUsuario\": 1, \"error\": \"Cuenta no encontrada\" }"))
            )
    })
    @ExceptionHandler(CuentaNoExistenteException.class)
    public ResponseEntity<Map<String, Object>> handleCuentaNoExistente(CuentaNoExistenteException ex) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("id", ex.getId());
        errores.put("idUsuario", ex.getIdUsuario());
        errores.put("error", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Moneda inválida",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"tipoOperacion\": \"DEPOSITO\", \"tipoMoneda\": \"USD\", \"idCuenta\": " +
                                    "1, \"error\": \"Moneda inválida\" }"))
            )
    })
    @ExceptionHandler(MonedaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleMonedaInvalida(MonedaInvalidaException ex) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("tipoOperacion", ex.getTipoOperacion());
        errores.put("tipoMoneda", ex.getTipoMoneda());
        errores.put("idCuenta", ex.getIdCuenta());
        errores.put("error", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Saldo insuficiente",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"saldo\": 100, \"tipoMoneda\": \"ARS\", \"idCuenta\": 1, \"error\": " +
                                    "\"Saldo insuficiente\" }"))
            )
    })
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("saldo", ex.getSaldo());
        errores.put("tipoMoneda", ex.getTipoMoneda());
        errores.put("idCuenta", ex.getIdCuenta());
        errores.put("error", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Pago no existente",
                    content = @Content(schema = @Schema(
                            implementation = Map.Entry.class),
                            examples = @ExampleObject(value = "{ \"id\": 1, \"error\": \"Pago no existente\" }"))
            )
    })
    @ExceptionHandler(PagoNoExistenteExcepcion.class)
    public ResponseEntity<Map<String, Object>> handlePagoNoExistente(PagoNoExistenteExcepcion ex) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("id", ex.getId());
        errores.put("error", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            errores.put(violation.getPropertyPath().toString(), violation.getMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
