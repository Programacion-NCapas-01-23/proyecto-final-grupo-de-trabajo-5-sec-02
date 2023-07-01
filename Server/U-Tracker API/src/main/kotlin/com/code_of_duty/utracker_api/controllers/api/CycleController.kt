package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.services.api.cycle.CycleService
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import com.code_of_duty.utracker_api.utils.GeneralUtils
import com.code_of_duty.utracker_api.utils.UnauthorizedException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
    private val cycleService: CycleService,
    private val generalUtils: GeneralUtils
) {
    @Operation(
        summary = "Get all cycles",
        description = "Get all cycles",

        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = StudentCycleResponseDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    @GetMapping("/")
    @SecurityRequirement(name = "APIAuth")
    fun getAllCycles(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val studentCode = generalUtils.extractJWT(request)
        val cycles = cycleService.getAllCycles(studentCode)
        return ResponseEntity.ok(cycles)
    }

    @Operation(
        summary = "Get all cycles created by the student",
        description = "Get all student cycles",
        security = [SecurityRequirement(name = "ApiAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = StudentCycleDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    @GetMapping("/getStudentCycles")
    @SecurityRequirement(name = "APIAuth")
    fun getStudentCycles(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return try {
            val studentCode = generalUtils.extractJWT(request)
            val cycles = cycleService.getStudentCycles(studentCode)
            ResponseEntity.ok(cycles)
        } catch (e: UnauthorizedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @Operation(
        summary = "Create a new cycle",
        description = "Create a new cycle",
        security = [SecurityRequirement(name = "ApiAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycle created successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    @PostMapping("/createCycle")
    @SecurityRequirement(name = "APIAuth")
    fun createStudentCycle(
        request: HttpServletRequest,
        @RequestBody body: NewStudentCycleDto
    ): ResponseEntity<Any> {
        return try {
            val studentCode = generalUtils.extractJWT(request)
            cycleService.createStudentCycle(
                studentCode = studentCode,
                cycleType = body.cycleType,
                year = body.year
            )
            ResponseEntity(MessageDto("Cycle created successfully"), HttpStatus.OK)
        } catch (e: UnauthorizedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (e: ExceptionNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }

    @Operation(
        summary = "Add a subject to cycle",
        description = "Add a subject to a student cycle",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subject added successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    @PostMapping("/addSubject")
    @SecurityRequirement(name = "APIAuth")
    fun addSubjectToStudentCycle(
        request: HttpServletRequest,
        @RequestBody body: SubjectInStudentCycleDto
    ): ResponseEntity<Any> {
        return try {
            cycleService.addSubjectToStudentPerCycle(
                studentCycleId = UUID.fromString(body.studentCycleId),
                subjectCode = body.subjectCode
            )
            ResponseEntity(
                MessageDto("Subject added successfully"),
                HttpStatus.OK
            )
        } catch (e: UnauthorizedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (e: ExceptionNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }

}