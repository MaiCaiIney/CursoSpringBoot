package com.utn.springboot.billeteravirtual.entity;

import com.utn.springboot.billeteravirtual.entity.transacciones.TransaccionEntity;
import com.utn.springboot.billeteravirtual.types.TipoCuenta;
import com.utn.springboot.billeteravirtual.types.TipoMoneda;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuentas")
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(nullable = false, unique = true, length = 22, updatable = false)
    private String cbu;

    @Column(nullable = false, length = 50)
    private String alias;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private TipoMoneda tipoMoneda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    private List<TransaccionEntity> transacciones;

    public CuentaEntity() {
    }

    public CuentaEntity(TipoCuenta tipoCuenta, String cbu, String alias, BigDecimal balance, TipoMoneda tipoMoneda, UsuarioEntity usuario) {
        this.tipoCuenta = tipoCuenta;
        this.cbu = cbu;
        this.alias = alias;
        this.balance = balance;
        this.tipoMoneda = tipoMoneda;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<TransaccionEntity> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionEntity> transacciones) {
        this.transacciones = transacciones;
    }
}
