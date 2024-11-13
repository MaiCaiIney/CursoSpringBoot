package com.utn.springboot.billeteravirtual.repository.entity.security;

import com.utn.springboot.billeteravirtual.repository.entity.Auditable;
import com.utn.springboot.billeteravirtual.repository.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "credenciales")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredencialEntity extends Auditable<String> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, length = 20)
    private String user;

    @Column(name = "password", nullable = false)
    private String pass;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true)
    private UsuarioEntity usuario;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "perfil_id")
    @Getter
    @Setter
    private PerfilEntity perfil;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "credenciales_roles",
            joinColumns = @JoinColumn(name = "credencial_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @Getter
    private final Set<RolEntity> roles = new HashSet<>();

    public CredencialEntity(String user, String pass, UsuarioEntity usuario, PerfilEntity perfil) {
        this.user = user;
        this.pass = pass;
        this.usuario = usuario;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        perfil.getRoles().forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol.getNombre().name())));
        roles.forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol.getNombre().name())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void agregarRol(RolEntity rol) {
        this.roles.add(rol);
    }
}
