package com.code_of_duty.utracker_api.config


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import org.springframework.context.annotation.Configuration


@Configuration
@SecuritySchemes(
    io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
)
class SwaggerConfig {
// Configuration options if needed
}
