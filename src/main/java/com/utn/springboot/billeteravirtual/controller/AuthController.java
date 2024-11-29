package com.utn.springboot.billeteravirtual.controller;

import com.utn.springboot.billeteravirtual.config.security.JwtService;
import com.utn.springboot.billeteravirtual.controller.dto.AuthRequest;
import com.utn.springboot.billeteravirtual.controller.dto.AuthResponse;
import com.utn.springboot.billeteravirtual.repository.entity.security.CredencialEntity;
import com.utn.springboot.billeteravirtual.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;

    private final AuthService authenticationService;

    @Autowired
    public AuthController(JwtService jwtService, AuthService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public AuthResponse authenticate(@RequestBody AuthRequest loginUserDto) {
        CredencialEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return new AuthResponse(jwtToken);
    }

    @GetMapping("/me")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes(); // Devuelve los atributos del usuario autenticado
    }
}
