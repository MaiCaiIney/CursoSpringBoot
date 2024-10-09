package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.entity.CuentaEntity;
import com.utn.springboot.billeteravirtual.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.entity.UsuarioEntity;
import com.utn.springboot.billeteravirtual.exception.*;
import com.utn.springboot.billeteravirtual.mapper.CuentaMapper;
import com.utn.springboot.billeteravirtual.model.cuentas.Cuenta;
import com.utn.springboot.billeteravirtual.model.cuentas.Transaccion;
import com.utn.springboot.billeteravirtual.repository.CuentaRepository;
import com.utn.springboot.billeteravirtual.repository.TransaccionRepository;
import com.utn.springboot.billeteravirtual.repository.UsuarioRepository;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import com.utn.springboot.billeteravirtual.types.TipoTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuentaMapper cuentaMapper;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository,
                         TransaccionRepository transaccionRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.transaccionRepository = transaccionRepository;
        this.cuentaMapper = cuentaMapper;
    }

    // Crear una cuenta.
    // Si al momento de crear la cuenta el balance es mayor a 0, se creará una transacción inicial de tipo DEPOSITO.
    public Cuenta crearCuenta(Cuenta cuenta, Long idUsuario) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNoExistenteException(idUsuario));

        CuentaEntity entity = new CuentaEntity();
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setTipoMoneda(cuenta.getTipoMoneda());
        entity.setBalance(cuenta.getBalance());
        entity.setUsuario(usuario);
        entity = cuentaRepository.save(entity);

        return cuentaMapper.toModel(entity);
    }

    // Obtener todas las cuentas de un usuario
    public List<Cuenta> obtenerCuentasPorUsuario(Long idUsuario) {
        List<CuentaEntity> entidades = cuentaRepository.findByUsuarioId(idUsuario);
        return entidades.stream().map(cuentaMapper::toModel).toList();
    }

    // Obtener una cuenta por ID. Si no existe, lanzará una excepción CuentaNoExistenteException.
    public Cuenta obtenerCuentaPorId(Long id) throws CuentaNoExistenteException {
        CuentaEntity entity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
        return cuentaMapper.toModel(entity);
    }

    // Depósito de dinero en una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda del depósito no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el depósito es exitoso, se creará una transacción de tipo DEPOSITO.
    public Transaccion depositar(Long id, BigDecimal monto, TipoMoneda moneda) throws CuentaNoExistenteException, MonedaInvalidaException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));

        if (!cuentaEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.DEPOSITO);
        }

        TransaccionEntity transaccionEntity = new TransaccionEntity(TipoTransaccion.DEPOSITO, monto, cuentaEntity);
        transaccionEntity = transaccionRepository.save(transaccionEntity);

        return cuentaMapper.toModel(transaccionEntity);
    }

    // Retiro de dinero de una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda del retiro no coincide con la moneda de la cuenta, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si el retiro es exitoso, se creará una transacción de tipo RETIRO.
    public Transaccion retirar(Long id, BigDecimal monto, TipoMoneda moneda) throws CuentaNoExistenteException, MonedaInvalidaException,
            SaldoInsuficienteException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));

        if (!cuentaEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.RETIRO);
        }

        if (esBalanceInsuficiente(cuentaEntity.getBalance(), monto)) {
            throw new SaldoInsuficienteException(id, cuentaEntity.getBalance(), moneda);
        } else {
            BigDecimal nuevoBalance = cuentaEntity.getBalance().subtract(monto);
            cuentaEntity.setBalance(nuevoBalance);
            cuentaRepository.save(cuentaEntity);

            TransaccionEntity transaccionEntity = new TransaccionEntity(TipoTransaccion.RETIRO, monto, cuentaEntity);
            transaccionEntity = transaccionRepository.save(transaccionEntity);

            return cuentaMapper.toModel(transaccionEntity);
        }
    }

    // Transferencia de dinero entre cuentas.
    // Si la cuenta origen o destino no existe, lanzará una excepción CuentaNoExistenteException.
    // Si la moneda de la transferencia no coincide con la moneda de alguna de las cuentas, lanzará una excepción MonedaInvalidaException.
    // Si el saldo de la cuenta origen es insuficiente, lanzará una excepción SaldoInsuficienteException.
    // Si la transferencia es exitosa, se creará una transacción de tipo TRANSFERENCIA en ambas cuentas.
    public Transaccion transferir(Long idOrigen, Long idDestino, BigDecimal monto, TipoMoneda moneda) throws CuentaNoExistenteException,
            MonedaInvalidaException,
            SaldoInsuficienteException {
        CuentaEntity cuentaOrigenEntity = cuentaRepository.findById(idOrigen).orElseThrow(() -> new CuentaNoExistenteException(idOrigen));
        CuentaEntity cuentaDestinoEntity =
                cuentaRepository.findById(idDestino).orElseThrow(() -> new CuentaNoExistenteException(idDestino));

        if (!cuentaOrigenEntity.getTipoMoneda().equals(moneda) || !cuentaDestinoEntity.getTipoMoneda().equals(moneda)) {
            throw new MonedaInvalidaException(TipoOperacion.TRANSFERENCIA);
        }

        if (esBalanceInsuficiente(cuentaOrigenEntity.getBalance(), monto)) {
            throw new SaldoInsuficienteException(idOrigen, cuentaOrigenEntity.getBalance(), moneda);
        } else {
            BigDecimal nuevoBalanceOrigen = cuentaOrigenEntity.getBalance().subtract(monto);
            cuentaOrigenEntity.setBalance(nuevoBalanceOrigen);

            BigDecimal nuevoBalanceDestino = cuentaDestinoEntity.getBalance().add(monto);
            cuentaDestinoEntity.setBalance(nuevoBalanceDestino);

            cuentaRepository.save(cuentaOrigenEntity);
            cuentaRepository.save(cuentaDestinoEntity);

            TransaccionEntity transaccionOrigenEntity = new TransaccionEntity(TipoTransaccion.TRANSFERENCIA, monto, cuentaOrigenEntity);
            transaccionOrigenEntity = transaccionRepository.save(transaccionOrigenEntity);

            TransaccionEntity transaccionDestinoEntity = new TransaccionEntity(TipoTransaccion.TRANSFERENCIA, monto, cuentaDestinoEntity);
            transaccionDestinoEntity = transaccionRepository.save(transaccionDestinoEntity);

            return cuentaMapper.toModel(transaccionOrigenEntity);
        }
    }

    // Eliminar una cuenta.
    // Si la cuenta no existe, lanzará una excepción CuentaNoExistenteException.
    // Devolverá true si la cuenta fue eliminada, false en caso contrario.
    public void eliminarCuenta(Long id) throws CuentaNoExistenteException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
        cuentaRepository.delete(cuentaEntity);
    }

    public List<Transaccion> buscarTransacciones(Long id) throws CuentaNoExistenteException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElseThrow(() -> new CuentaNoExistenteException(id));
        return cuentaEntity.getTransacciones().stream().map(cuentaMapper::toModel).toList();
    }

    private boolean esBalanceInsuficiente(BigDecimal saldoDisponible, BigDecimal montoTransaccion) {
        return saldoDisponible.compareTo(montoTransaccion) < 0;
    }
}
