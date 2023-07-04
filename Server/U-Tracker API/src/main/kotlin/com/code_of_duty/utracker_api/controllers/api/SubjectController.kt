package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.services.api.subject.SubjectService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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

    @Operation(
        summary = "Update subject",
        description = "Update subject",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subject updated",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
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
            ),
            ApiResponse(
                responseCode = "404",
                description = "Subject not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @PatchMapping("/updateSubject")
    @SecurityRequirement(name = "APIAuth")
    fun updateSubject(
        request: HttpServletRequest,
        @Valid @RequestBody body: UpdateSubjectStateDto
    ): ResponseEntity<Any> {
        val studentCode = generalUtils.extractJWT(request)
        val subjectCode = body.subjectCode
        val completed = body.state
        val grade = body.grade

        return try {
            subjectService.updateSubjectCompletion(studentCode, subjectCode, completed, grade)
            ResponseEntity.ok(MessageDto("Subject updated"))
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto("Subject not found"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto("Internal server error"))
        }
    }

    @Operation(
        summary = "Set assessment",
        description = "Set assessment to a subject",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Assessment added",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
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
            ),
            ApiResponse(
                responseCode = "404",
                description = "Subject not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/setAssessment")
    @SecurityRequirement(name = "APIAuth")
    fun setAssessment(
        request: HttpServletRequest,
        @Valid @RequestBody body: AssessmentDto
    ): ResponseEntity<Any> {
        val subjectCode = body.subjectCode
        val name = body.name
        val percentage = body.percentage
        val date = body.date
        val grade = body.grade
        return try {
            subjectService.setAssessment(subjectCode, name, percentage, date, grade)
            ResponseEntity.ok(MessageDto("Assessment added"))
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto("Subject not found"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto("Internal server error"))
        }
    }

    @Operation(
        summary = "Get estimate grades",
        description = "Get estimate grades for subject",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Assessments found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RemainingAssessmentDto::class)
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
            ),
            ApiResponse(
                responseCode = "404",
                description = "Subject not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/calculateEstimateGrades")
    @SecurityRequirement(name = "APIAuth")
    fun calculateEstimateGrades(
        request: HttpServletRequest,
        @Valid @RequestBody body: EstimateGradeDto
    ): ResponseEntity<Any> {
        val subject = body.subjectCode
        return try {
            val estimateGrade = subjectService.calculateEstimateGrades(subject)
            ResponseEntity.ok(estimateGrade)
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto("Subject not found"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto("Internal server error"))
        }
    }

    @Operation(
        summary = "Calculate cum",
        description = "Calculate cum for student",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cum calculated",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CumDto::class)
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
            ),
            ApiResponse(
                responseCode = "404",
                description = "Student not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/calculateCum")
    @SecurityRequirement(name = "APIAuth")
    fun calculateCum(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val studentCode = generalUtils.extractJWT(request)
        return try {
            val cum = subjectService.calculateCum(studentCode)
            ResponseEntity.ok(cum)
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto("Student not found"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto("Internal server error"))
        }
    }
}