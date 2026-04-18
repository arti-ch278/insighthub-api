package com.artichourey.insighthub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;

import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("InsightHub API")
                        .description("REST APIs for InsightHub Blogging Platform")
                        .version("1.0"))

                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))

                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }
}