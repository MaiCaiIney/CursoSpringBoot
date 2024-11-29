package com.utn.springboot.billeteravirtual.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("/workspace")
public class GoogleWorkspaceController {
    private static final String APPLICATION_NAME = "Google Drive API Java Integration";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @GetMapping("/drive/files")
    public String listDriveFiles(Model model,
                                 @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws GeneralSecurityException, IOException {

        // Obtener el token de acceso desde el cliente autorizado
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Crear la instancia del servicio de Google Drive utilizando el token de acceso
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, request -> {
            request.getHeaders().setAuthorization("Bearer " + accessToken);
        })
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Obtener la lista de archivos
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        model.addAttribute("files", files);
        return "driveFileList";
    }
}
