package com.code_of_duty.utracker_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UTrackerApiApplication
fun main(args: Array<String>) {
    runApplication<UTrackerApiApplication>(*args)
}
