package org.example.pr4.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "КРУТОЕ API",
                description = "API проекта",
                version = "0.0.1"
        )
)
public class OpenApiConfig {
}