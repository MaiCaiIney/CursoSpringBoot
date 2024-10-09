package com.utn.springboot.billeteravirtual.controller;

import com.utn.springboot.billeteravirtual.dto.CuentaRequest;
import com.utn.springboot.billeteravirtual.mapper.CuentaMapper;
import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import com.utn.springboot.billeteravirtual.service.CuentaService;
import com.utn.springboot.billeteravirtual.service.UsuarioService;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    // Estos atributos son finales y se inicializan en el constructor.
    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    // El constructor recibe los servicios necesarios para el controlador.
    // La anotación @Autowired indica a Spring que debe inyectar estas dependencias al momento de crear una instancia de la clase.
    @Autowired
    public CuentaController(CuentaService cuentaService, CuentaMapper cuentaMapper) {
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
    }

    @Operation(summary = "Obtener todas las cuentas de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida con éxito")
    @GetMapping
    public List<Cuenta> obtenerCuentas(
            @Parameter(description = "Identificador único del usuario", example = "1")
            @RequestParam Long idUsuario) {
        return cuentaService.obtenerCuentasPorUsuario(idUsuario);
    }

    @Operation(summary = "Crear una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de la cuenta inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta crearCuenta(
            @Parameter(description = "Información obligatoria para crear una cuenta")
            @Valid @RequestBody CuentaRequest request) {
        Cuenta cuenta = cuentaMapper.toModel(request);
        return cuentaService.crearCuenta(cuenta, request.getIdUsuario());
    }

    @Operation(summary = "Obtener una cuenta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta obtenida con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public Cuenta obtenerCuentaPorId(
            @Parameter(description = "Identificador único de la cuenta", example = "1")
            @PathVariable Long id) {
        return cuentaService.obtenerCuentaPorId(id);
    }

    @Operation(summary = "Depositar dinero en una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito realizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del depósito inválidos")
    })
    @PostMapping("/{id}/depositar")
    public Transaccion depositar(
            @Parameter(description = "Identificador único de la cuenta destino del depósito", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Monto a depositar", example = "100.0")
            @RequestParam BigDecimal monto,
            @Parameter(description = "Tipo de moneda del depósito", example = "ARS")
            @RequestParam TipoMoneda moneda) {
        return cuentaService.depositar(id, monto, moneda);
    }

    @Operation(summary = "Retirar dinero de una cuenta")
    @PostMapping("/{id}/retirar")
    public Transaccion retirar(
            @Parameter(description = "Identificador único de la cuenta origen del retiro", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Monto a retirar", example = "50.0")
            @RequestParam BigDecimal monto,
            @Parameter(description = "Tipo de moneda del retiro", example = "ARS")
            @RequestParam TipoMoneda moneda) {
        return cuentaService.retirar(id, monto, moneda);
    }

    @Operation(summary = "Transferir dinero entre cuentas")
    @PostMapping("/transferir")
    public Transaccion transferir(
            @Parameter(description = "Identificador único de la cuenta origen de la transferencia", example = "1")
            @RequestParam Long idOrigen,
            @Parameter(description = "Identificador único de la cuenta destino de la transferencia", example = "2")
            @RequestParam Long idDestino,
            @Parameter(description = "Monto a transferir", example = "75.0")
            @RequestParam BigDecimal monto,
            @Parameter(description = "Tipo de moneda de la transferencia", example = "ARS")
            @RequestParam TipoMoneda moneda) {
        return cuentaService.transferir(idOrigen, idDestino, monto, moneda);
    }

    @Operation(summary = "Eliminar una cuenta")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCuenta(
            @Parameter(description = "Identificador único de la cuenta a eliminar", example = "1")
            @PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
    }

    @Operation(summary = "Buscar transacciones de una cuenta")
    @GetMapping("/{id}/transacciones")
    public List<Transaccion> buscarTransacciones(
            @Parameter(description = "Identificador único de la cuenta", example = "1")
            @PathVariable Long id) {
        return cuentaService.buscarTransacciones(id);
    }
}
