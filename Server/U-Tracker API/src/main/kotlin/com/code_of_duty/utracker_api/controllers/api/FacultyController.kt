package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.StudentDegreeDto
import com.code_of_duty.utracker_api.data.dtos.StudentFacultyDto
import com.code_of_duty.utracker_api.services.api.faculty.FacultyService
import com.code_of_duty.utracker_api.utils.UnauthorizedException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("\${api.base-path}/faculty")
@Tag(name = "API")
class FacultyController (
    private val facultyService: FacultyService
) {
    @Operation(
        summary = "Get all degrees",
        description = "Get all degrees",

        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Degrees retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = StudentFacultyDto::class
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
    fun getAllFaculties(
        @RequestParam(required = false) id: UUID?,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val faculties = facultyService.getAllFaculties()
        return ResponseEntity.ok(faculties)
    }
}