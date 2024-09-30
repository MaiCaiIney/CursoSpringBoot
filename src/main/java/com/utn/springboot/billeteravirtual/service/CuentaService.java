package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.exception.CuentaNoExistenteException;
import com.utn.springboot.billeteravirtual.exception.MonedaInvalidaException;
import com.utn.springboot.billeteravirtual.exception.SaldoInsuficienteException;
import com.utn.springboot.billeteravirtual.exception.TipoOperacion;
import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.TipoMoneda;
import com.utn.springboot.billeteravirtual.model.cuentas.movimientos.Movimiento;
import com.utn.springboot.billeteravirtual.model.cuentas.movimientos.TipoMovimiento;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {
    private final List<Cuenta> cuentas = new ArrayList<>();

    // Crear una cuenta.
    // Si al momento de crear la cuenta el balance es mayor a 0, se creará un movimiento inicial de tipo DEPOSITO.
    public Cuenta crearCuenta(Cuenta cuenta) {
        cuenta.setId((long) (cuentas.size() + 1));

        if (cuenta.getBalance() > 0) {
            Movimiento movimiento = new Movimiento(cuenta.getId(), TipoMovimiento.DEPOSITO, cuenta.getBalance(), cuenta.getTipoMoneda());
            cuenta.agregarMovimiento(movimiento);
        }

        cuentas.add(cuenta);
        return cuenta;
    }

    // Obtener todas las cuentas de un usuario
    public List<Cuenta> obtenerCuentasPorUsuario(Long idUsuario) {
        return cuentas.stream()
                .filter(c -> c.getUsuario().getId().equals(idUsuario))
                .toList();
    }

    // Obtener una cuenta por ID. Si no existe, lanzará una excepción CuentaNoExistenteException.
    public Cuenta obtenerCuentaPorId(Long id) throws CuentaNoExistenteException {
        return cuentas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CuentaNoExistenteException(id));
    }

    // Depósito de dinero en una cuenta.
    // Si la moneda del depósito no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el depósito es exitoso, se creará un movimiento de tipo DEPOSITO.
    public Cuenta depositar(Long id, Double monto, TipoMoneda moneda) throws MonedaInvalidaException {
        Cuenta cuenta = obtenerCuentaPorId(id);
        if (!cuenta.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.DEPOSITO);
        }
        cuenta.setBalance(cuenta.getBalance() + monto);

        Movimiento movimiento = new Movimiento(cuenta.getId(), TipoMovimiento.DEPOSITO, monto, moneda);
        cuenta.agregarMovimiento(movimiento);

        return cuenta;
    }

    // Retiro de dinero de una cuenta.
    // Si la moneda del retiro no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si el retiro es exitoso, se creará un movimiento de tipo RETIRO.
    public Cuenta retirar(Long id, Double monto, TipoMoneda moneda) throws MonedaInvalidaException, SaldoInsuficienteException {
        Cuenta cuenta = obtenerCuentaPorId(id);
        if (!cuenta.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.RETIRO);
        }
        if (cuenta.getBalance() >= monto) {
            cuenta.setBalance(cuenta.getBalance() - monto);

            Movimiento movimiento = new Movimiento(cuenta.getId(), TipoMovimiento.RETIRO, monto, moneda);
            cuenta.agregarMovimiento(movimiento);

        } else {
            throw new SaldoInsuficienteException(id, cuenta.getBalance(), moneda);
        }
        return cuenta;
    }

    // Transferencia de dinero entre cuentas.
    // Si la moneda de la transferencia no coincide con la moneda de alguna de las cuentas, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta origen es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si la transferencia es exitosa, se creará un movimiento de tipo TRANSFERENCIA en ambas cuentas.
    public List<Cuenta> transferir(Long idOrigen, Long idDestino, Double monto, TipoMoneda moneda) throws MonedaInvalidaException,
            SaldoInsuficienteException {
        Cuenta cuentaOrigen = obtenerCuentaPorId(idOrigen);
        Cuenta cuentaDestino = obtenerCuentaPorId(idDestino);

        if (!cuentaOrigen.getTipoMoneda().equals(moneda) || !cuentaDestino.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.TRANSFERENCIA);
        }

        if (cuentaOrigen.getBalance() >= monto) {
            cuentaOrigen.setBalance(cuentaOrigen.getBalance() - monto);
            cuentaDestino.setBalance(cuentaDestino.getBalance() + monto);

            Movimiento movimientoOrigen = new Movimiento(cuentaOrigen.getId(), TipoMovimiento.TRANSFERENCIA, monto, moneda);
            cuentaOrigen.agregarMovimiento(movimientoOrigen);

            Movimiento movimientoDestino = new Movimiento(cuentaDestino.getId(), TipoMovimiento.TRANSFERENCIA, monto, moneda);
            cuentaDestino.agregarMovimiento(movimientoDestino);

            return List.of(cuentaOrigen, cuentaDestino);
        } else {
            throw new SaldoInsuficienteException(idOrigen, cuentaOrigen.getBalance(), moneda);
        }
    }

    // Eliminar una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Devolverá true si la cuenta fue eliminada, false en caso contrario.
    public boolean eliminarCuenta(Long id) throws CuentaNoExistenteException {
        Cuenta cuenta = obtenerCuentaPorId(id);
        return cuentas.remove(cuenta);
    }
}
