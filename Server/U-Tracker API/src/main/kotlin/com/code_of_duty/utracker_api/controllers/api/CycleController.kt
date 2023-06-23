package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.data.models.Degree
import com.code_of_duty.utracker_api.services.api.degree.DegreeService
import com.code_of_duty.utracker_api.utils.JwtUtils
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("\${api.base-path}/cycle")
@Tag(name = "API")
class CycleController(
    private val degreeService: DegreeService,
    private val jwtUtils: JwtUtils
) {
    @GetMapping("/")
    fun getAllCycles(
        @RequestParam(required = false) id: UUID,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        TODO()
    }
    @GetMapping("/getCycleByStudent")
    @SecurityRequirement(name = "ApiAuth")
    fun getCycleByStudent(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/createCycle")
    @SecurityRequirement(name = "ApiAuth")
    fun createCycle(
        request: HttpServletRequest,
        @RequestBody body: Any//TODO: Add CycleRequest
    ): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/addSubject")
    @SecurityRequirement(name = "ApiAuth")
    fun addSubject(
        request: HttpServletRequest,
        @RequestBody body: Any//TODO: Add SubjectRequest
    ): ResponseEntity<Any> {
        TODO()
    }
}