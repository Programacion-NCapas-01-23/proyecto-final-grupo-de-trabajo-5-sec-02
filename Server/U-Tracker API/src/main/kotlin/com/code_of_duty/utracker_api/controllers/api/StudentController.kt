package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.ChangePasswordDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.dtos.StudentResponseDto
import com.code_of_duty.utracker_api.services.api.student.StudentService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base-path}/student")
@Tag(name = "API")
class StudentController(private val studentService: StudentService) {

    @Autowired
    private lateinit var generalUtils: GeneralUtils

    @Operation(
        summary = "Get student by code",
        description = "Get student by code",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Student found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StudentResponseDto::class)
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
            )
        ]
    )
    @GetMapping("/getStudent")
    fun getStudent( request: HttpServletRequest ): ResponseEntity<StudentResponseDto> {
        val code = generalUtils.extractJWT(request)

        val student = studentService.getStudent(code) ?: throw StudentNotFoundException("Student not found")

        val response = StudentResponseDto(
            code = student.code,
            username = student.username,
            image = student.image,
            cum = student.cum,
            degree = student.degree
        )

        return ResponseEntity(response, HttpStatus.OK)
    }

    @Operation(
        summary = "Update student Information",
        description = "Update student Information",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Student updated successfully",
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
                description = "Student not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @PatchMapping("/updateStudent")
    fun updateStudent(
        request: HttpServletRequest,
        @Parameter(description = "Student information to update")
        @Valid @RequestBody student: StudentRequestDto
    ): ResponseEntity<MessageDto> {
        val code = generalUtils.extractJWT(request)

        studentService.updateStudent(code, student)

        return ResponseEntity(
            MessageDto("Student updated successfully"),
            HttpStatus.OK
        )
    }

    @Operation(
        summary = "Change student password",
        description = "Change student password",
        security = [SecurityRequirement(name = "APIAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Password changed successfully",
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
                description = "Student not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @PatchMapping("/changePassword")
    @SecurityRequirement(name = "APIAuth")
    fun changePassword(
        request: HttpServletRequest,
        @Parameter(description = "Student information to update")
        @Valid @RequestBody student: ChangePasswordDto
    ): ResponseEntity<MessageDto> {

        val code = generalUtils.extractJWT(request)

        studentService.changePassword(code, student)

        return ResponseEntity(
            MessageDto("Password changed successfully"),
            HttpStatus.OK
        )
    }

    @PatchMapping("/changeImage")
    @Operation(summary = "Change student image")
    @SecurityRequirement(name = "APIAuth")
    fun changeImage(
        request: HttpServletRequest,
        @Parameter(description = "Student image to update")
        @Valid @RequestBody image: String
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @GetMapping("/cumCalculation")
    @Operation(summary = "Calculate student cum")
    @SecurityRequirement(name = "APIAuth")
    fun cumCalculation(
        request: HttpServletRequest
    ): ResponseEntity<MessageDto> {
        TODO()
    }


}