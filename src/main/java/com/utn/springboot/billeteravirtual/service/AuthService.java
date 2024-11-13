package com.utn.springboot.billeteravirtual.service;

import com.utn.springboot.billeteravirtual.controller.dto.AuthRequest;
import com.utn.springboot.billeteravirtual.repository.CredencialRepository;
import com.utn.springboot.billeteravirtual.repository.entity.security.CredencialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CredencialRepository credencialRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(CredencialRepository credencialRepository, AuthenticationManager authenticationManager) {
        this.credencialRepository = credencialRepository;
        this.authenticationManager = authenticationManager;
    }

    public CredencialEntity authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return credencialRepository.findByUser(input.username()).orElseThrow();
    }
}
