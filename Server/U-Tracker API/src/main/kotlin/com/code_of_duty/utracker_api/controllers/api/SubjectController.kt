package com.code_of_duty.utracker_api.controllers.api

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base-path}/subject")
@Tag(name = "API")
class SubjectController {
    @GetMapping("/getSubject")
    fun getSubject(@RequestParam(required = false) name: String): ResponseEntity<Any> {
        TODO()
    }

    @GetMapping("/getSubjectsByStudent")
    @SecurityRequirement(name = "ApiAuth")
    fun getSubjectByStudent(): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/{id}")
    @SecurityRequirement(name = "ApiAuth")
    fun setAssessment(
        request: HttpServletRequest,
        @Valid @RequestBody body: Any,//TODO: Add AssessmentRequest
        @PathVariable id: String
    ): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/calculateEstimateGrades")
    @SecurityRequirement(name = "ApiAuth")
    fun calculateEstimateGrades(
        request: HttpServletRequest,
        @Valid @RequestBody body: Any//TODO: Add SubjectRequest
    ): ResponseEntity<Any> {
        TODO()
    }
}