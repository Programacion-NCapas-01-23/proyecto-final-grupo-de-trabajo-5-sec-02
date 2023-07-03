package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.services.api.subject.SubjectService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base-path}/subject")
@Tag(name = "API")
class SubjectController {

    @Autowired
    lateinit var subjectService: SubjectService
    @Autowired
    lateinit var generalUtils: GeneralUtils

    @Operation(
        summary = "Get all subjects",
        description = "Get all subjects",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subjects found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SubjectDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/getAllSubjects")
    fun getAllSubjects(
        @RequestParam(name = "nameFilter", required = false) nameFilter: String?,
        @RequestParam(name = "sortBy", required = false) sortBy: String?,
        @RequestParam(name = "degreeFilter", required = false) degreeFilter: String?,
        @RequestParam(name = "pensumFilter", required = false) pensumFilter: String?,
        @RequestParam(name = "facultyFilter", required = false) facultyFilter: String?
    ): List<SubjectDto> {
        return subjectService.getAllSubjects(nameFilter, sortBy, degreeFilter, pensumFilter, facultyFilter)
    }

    @PutMapping("/completion")
    fun updateSubjectCompletion(
        @RequestParam subjectId: String,
        @RequestParam passed: Boolean,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
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