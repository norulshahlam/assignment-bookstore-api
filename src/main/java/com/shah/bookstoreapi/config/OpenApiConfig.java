package com.shah.bookstoreapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NORUL
 */
@Configuration
@SecurityScheme(
        name = "basicAuth", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(SpringDocProperties properties) {
        return new OpenAPI()
                .info(getInfo(properties)
                        .contact(getContact(properties)));
    }

    private Info getInfo(SpringDocProperties properties) {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .license(getLicense(properties));
    }

    private Contact getContact(SpringDocProperties properties) {
        return new Contact()
                .url(properties.getUrl())
                .name(properties.getName())
                .email(properties.getEmail());
    }

    private License getLicense(SpringDocProperties properties) {
        return new License()
                .name(properties.getLicenseName())
                .url(properties.getLicenseUrl());
    }
}
