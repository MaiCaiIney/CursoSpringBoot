package com.utn.springboot.billeteravirtual.model.cuentas;

import com.utn.springboot.billeteravirtual.model.Usuario;
import com.utn.springboot.billeteravirtual.types.TipoCuenta;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;

import java.math.BigDecimal;

public class Cuenta implements Comparable<Cuenta> {
    private final Long id;
    private final TipoCuenta tipoCuenta;
    private final String cbu;
    private final String alias;
    private final TipoMoneda tipoMoneda;
    private final BigDecimal balance;

    private Cuenta(CuentaBuilder builder) {
        this.id = builder.id;
        this.tipoCuenta = builder.tipoCuenta;
        this.cbu = builder.cbu;
        this.alias = builder.alias;
        this.tipoMoneda = builder.tipoMoneda;
        this.balance = builder.balance;
    }

    @Override
    public int compareTo(Cuenta o) {
        return this.id.compareTo(o.id);
    }

    public Long getId() {
        return id;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return """
                {
                    "id": %d,
                    "balance": %.2f,
                    "tipoCuenta": "%s",
                    "tipoMoneda": "%s"
                }
                """.formatted(id, balance, tipoCuenta, tipoMoneda);
    }

    // El patrón Builder nos permite crear instancias de la clase Cuenta con una sintaxis más clara y legible.
    // En lugar de tener un constructor con muchos parámetros, podemos usar un objeto Builder para ir configurando los atributos de la
    // clase.
    public static class CuentaBuilder {
        private Long id;
        private String cbu;
        private String alias;
        private BigDecimal balance = BigDecimal.ZERO;
        private TipoCuenta tipoCuenta;
        private TipoMoneda tipoMoneda;
        private Usuario usuario;

        public CuentaBuilder() {
        }

        public CuentaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CuentaBuilder cbu(String cbu) {
            this.cbu = cbu;
            return this;
        }

        public CuentaBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public CuentaBuilder balance(BigDecimal balance) {
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
