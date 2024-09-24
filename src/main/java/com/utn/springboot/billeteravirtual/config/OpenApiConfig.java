package com.utn.springboot.billeteravirtual.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Billetera Virtual")
                              .description("Documentación de la API para la aplicación de Billetera Virtual")
                              .version("1.0.0")
                              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                                      .description("Documentación de referencia")
                                      .url("https://swagger.io/docs/"));
    }
}
