package com.utn.springboot.billeteravirtual.config.security;

import com.utn.springboot.billeteravirtual.repository.CredencialRepository;
import com.utn.springboot.billeteravirtual.repository.entity.CredencialEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final CredencialRepository credencialRepository;

    public JpaUserDetailsService(CredencialRepository credencialRepository) {
        this.credencialRepository = credencialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CredencialEntity credencial = credencialRepository.findByUser(username);
        if (credencial == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new User(credencial.getUser(), credencial.getPass(), List.of(new SimpleGrantedAuthority("user")));
    }
}
