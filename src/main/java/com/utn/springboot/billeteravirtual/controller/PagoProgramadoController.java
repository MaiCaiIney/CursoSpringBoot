package com.utn.springboot.billeteravirtual.controller;

import com.utn.springboot.billeteravirtual.controller.dto.PagoProgramadoRequest;
import com.utn.springboot.billeteravirtual.model.cuentas.PagoProgramado;
import com.utn.springboot.billeteravirtual.service.PagoProgramadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController("Pagos programados")
@RequestMapping("/usuarios/{id}/pagos")
public class PagoProgramadoController {
    private final PagoProgramadoService pagoProgramadoService;

    @Autowired
    public PagoProgramadoController(PagoProgramadoService pagoProgramadoService) {
        this.pagoProgramadoService = pagoProgramadoService;
    }

    @Operation(summary = "Programar un pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago programado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
            @ApiResponse(responseCode = "400", description = "Moneda inválida")
    }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PagoProgramado programarPago(@PathVariable Long id, @Valid @RequestBody PagoProgramadoRequest request) {
        return pagoProgramadoService.programarPago(mapRequestToModel(request), id);
    }

    @GetMapping
    public List<PagoProgramado> listarPagos(@PathVariable Long id) {
        return pagoProgramadoService.obtenerPagosProgramadosActivos(id);
    }

    @GetMapping("/historial")
    public List<PagoProgramado> listarHistorial(@PathVariable Long id) {
        return pagoProgramadoService.obtenerPagosProgramadosEjecutados(id);
    }

    @DeleteMapping("/{pagoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPago(@PathVariable Long id, @PathVariable Long pagoId) {
        pagoProgramadoService.cancelarPagoProgramado(pagoId);
    }

    @PutMapping("/{pagoId}")
    public PagoProgramado actualizarPago(@PathVariable Long id, @PathVariable Long pagoId, @RequestParam BigDecimal monto) {
        return pagoProgramadoService.actualizarMonto(pagoId, monto);
    }

    // Este e
    private PagoProgramado mapRequestToModel(PagoProgramadoRequest request) {
        return new PagoProgramado.Builder()
                .setTipoPago(request.tipoPagoProgramado())
                .setMonto(request.monto())
                .setFechaInicio(request.fechaInicio())
                .setPeriodicidad(request.periodicidad())
                .setIdCuentaOrigen(request.idCuentaOrigen())
                .setIdCuentaDestino(request.idCuentaDestino())
                .build();
    }
}
