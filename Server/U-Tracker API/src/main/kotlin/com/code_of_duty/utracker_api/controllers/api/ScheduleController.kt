package com.code_of_duty.utracker_api.controllers.api

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base-path}/schedule")
@Tag(name = "API")
class ScheduleController {
    @PostMapping("/generateSchedules")
    @SecurityRequirement(name = "ApiAuth")
    fun generateSchedules(
        request: HttpServletRequest,
        @RequestBody body: Any//TODO: Add ScheduleRequest
    ): ResponseEntity<Any> {
        TODO()
    }
}