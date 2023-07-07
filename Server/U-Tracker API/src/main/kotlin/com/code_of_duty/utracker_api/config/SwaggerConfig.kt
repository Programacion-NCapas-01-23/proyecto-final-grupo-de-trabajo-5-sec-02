package com.code_of_duty.utracker_api.config


import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@SecuritySchemes(
    SecurityScheme(
        name = "APIAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    ),
    SecurityScheme(
        name = "AdminAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
)
@OpenAPIDefinition(
    info = Info(
        title = "UTracker API",
        version = "1.0.0",
        description = "API for UTracker application and web page by Code of Duty",
        license = License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
        contact = Contact(
            name = "Code of Duty",
            email = "u.tracker.application@gmail.com"
        )
    ),
    security = [SecurityRequirement(name = "BearerAuth")],
    tags = [
        Tag(
            name = "Admin",
            description = "Admin operations",
        ),
        Tag(
            name = "API",
            description = "API operations"
        )
    ]
)
class SwaggerConfig
