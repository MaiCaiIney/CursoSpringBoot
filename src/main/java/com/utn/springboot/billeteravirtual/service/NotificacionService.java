package com.utn.springboot.billeteravirtual.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Async
    public void enviarNotificacion(String email, String mensaje) throws InterruptedException {
        System.out.println("Enviando notificación a " + email + " con mensaje: " + mensaje);
        Thread.sleep(600000);
    }
}
