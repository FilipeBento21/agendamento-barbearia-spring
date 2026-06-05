package com.filipe_bento.agendamento_barbearia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Agendamento de Barbearia")
                        .description("API REST para gerenciamento de clientes, barbeiros, serviços e agendamentos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Filipe Bento & Hytalo Leão")
                                .email("seuemail@email.com")));
    }
}