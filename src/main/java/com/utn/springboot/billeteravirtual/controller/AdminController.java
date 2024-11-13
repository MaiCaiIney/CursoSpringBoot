package com.utn.springboot.billeteravirtual.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${app.files.folder}")
    private String carpetaArchivos;

    @GetMapping
    public String admin(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hola " + userDetails.getUsername() + ", usted es un administrador";
    }

    @GetMapping("/archivos")
    public String archivos(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hola " + userDetails.getUsername() + ", usted es un administrador y tiene acceso a archivos";
    }

    @PostMapping(value = "/archivos", consumes = "multipart/form-data")
    public ResponseEntity<String> subirArchivo(@Parameter(description = "Archivo a subir") @RequestParam("file") MultipartFile file) {
        try {
            Path escritorioPath = obtenerRutaEscritorio();
            Path rutaArchivo = escritorioPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(rutaArchivo.toFile());
            return ResponseEntity.ok("Archivo subido correctamente: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/archivos/{nombreArchivo}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String nombreArchivo) {
        try {
            Path archivoPath = obtenerRutaEscritorio().resolve(nombreArchivo);
            Resource recurso = new UrlResource(archivoPath.toUri());

            if (!recurso.exists() || !recurso.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Tipo genérico para descarga
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                    .body(recurso);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    private Path obtenerRutaEscritorio() throws IOException {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        Path subcarpetaPath = Paths.get(desktopPath).resolve(carpetaArchivos);
        if (!Files.exists(subcarpetaPath)) {
            Files.createDirectories(subcarpetaPath);
        }
        return subcarpetaPath;
    }
}
