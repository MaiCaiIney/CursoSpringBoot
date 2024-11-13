package com.utn.springboot.billeteravirtual.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String admin(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hola " + userDetails.getUsername() + ", usted es un administrador";
    }

    @GetMapping("/archivos")
    public String archivos(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hola " + userDetails.getUsername() + ", usted es un administrador y tiene acceso a archivos";
    }
}
