package com.utn.springboot.billeteravirtual.model.cuentas;

import com.utn.springboot.billeteravirtual.model.Usuario;
import com.utn.springboot.billeteravirtual.model.cuentas.movimientos.Movimiento;

import java.util.ArrayList;
import java.util.List;

public class Cuenta implements Comparable<Cuenta> {
    private final List<Movimiento> movimientos;
    private Long id;
    private Double balance;
    private TipoCuenta tipoCuenta;
    private TipoMoneda tipoMoneda;
    private Usuario usuario;

    private Cuenta(CuentaBuilder builder) {
        this.id = builder.id;
        this.balance = builder.balance;
        this.tipoCuenta = builder.tipoCuenta;
        this.tipoMoneda = builder.tipoMoneda;
        this.usuario = builder.usuario;
        this.movimientos = builder.movimientos;
    }

    @Override
    public int compareTo(Cuenta o) {
        return this.id.compareTo(o.id);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void agregarMovimiento(Movimiento movimiento) {
        this.movimientos.add(movimiento);
    }

    @Override
    public String toString() {
        return """
                {
                    "id": %d,
                    "balance": %.2f,
                    "tipoCuenta": "%s",
                    "tipoMoneda": "%s",
                    "usuario": %s,
                    "movimientos": %s
                }
                """.formatted(id, balance, tipoCuenta, tipoMoneda, usuario, movimientos);
    }

    // El patrón Builder nos permite crear instancias de la clase Cuenta con una sintaxis más clara y legible.
    // En lugar de tener un constructor con muchos parámetros, podemos usar un objeto Builder para ir configurando los atributos de la
    // clase.
    public static class CuentaBuilder {
        private final List<Movimiento> movimientos = new ArrayList<>();
        private Long id;
        private Double balance = 0.0;
        private TipoCuenta tipoCuenta;
        private TipoMoneda tipoMoneda;
        private Usuario usuario;

        public CuentaBuilder() {
        }

        public CuentaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CuentaBuilder balance(Double balance) {
            this.balance = balance;
            return this;
        }

        public CuentaBuilder tipoCuenta(TipoCuenta tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
            return this;
        }

        public CuentaBuilder tipoMoneda(TipoMoneda tipoMoneda) {
            this.tipoMoneda = tipoMoneda;
            return this;
        }

        public CuentaBuilder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Cuenta build() {
            return new Cuenta(this);
        }
    }
}
