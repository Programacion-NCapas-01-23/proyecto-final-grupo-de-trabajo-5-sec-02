package com.code_of_duty.utracker_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    servers = [Server(url = "http://localhost:8080", description = "Local server")],
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "UTracker API",
        version = "1.0.0",
        description = "API for UTracker application and web page by Code of Duty"
    )
)

class UTrackerApiApplication
fun main(args: Array<String>) {
    runApplication<UTrackerApiApplication>(*args)
}
