package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.ChangePasswordDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.dtos.StudentResponseDto
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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
@Tag(name = "API", description = "API operations")
class StudentController(private val studentService: StudentService) {

    @Autowired
    private lateinit var generalUtils: GeneralUtils

    @GetMapping("/getStudent")
    @Operation(summary = "Get student by code")
    @SecurityRequirement(name = "APIAuth")
    fun getStudent( request: HttpServletRequest ): ResponseEntity<StudentResponseDto> {
        val token = generalUtils.extractJWT(request)
        //TODO ( "Implement validation JWT")
        val code = token
        val student = studentService.getStudent(code) ?: throw StudentNotFoundException("Student with code $code not found")

        val response = StudentResponseDto(
            code = student.code,
            username = student.username,
            image = student.image,
            cum = student.cum,
            degree = student.degree
        )

        return ResponseEntity(response, HttpStatus.OK)
    }

    /**
     * Update student information
     * @param request HttpServletRequest
     * @param student StudentRequestDto
     * @return MessageDto with success message
     */
    @PatchMapping("/updateStudent")
    @Operation(summary = "Update student Information")
    @SecurityRequirement(name = "APIAuth")
    fun updateStudent(
        request: HttpServletRequest,
        @Parameter(description = "Student information to update")
        @Valid @RequestBody student: StudentRequestDto
    ): ResponseEntity<MessageDto> {

        val token = generalUtils.extractJWT(request)
        //TODO ( "Implement validation JWT")
        val code = token

        studentService.updateStudent(code, student)

        return ResponseEntity(MessageDto("Student updated successfully"), HttpStatus.OK)
    }

    @PatchMapping("/changePassword")
    @Operation(summary = "Change student password")
    @SecurityRequirement(name = "APIAuth")
    fun changePassword(
        request: HttpServletRequest,
        @Parameter(description = "Student information to update")
        @Valid @RequestBody student: ChangePasswordDto
    ): ResponseEntity<MessageDto> {

        val token = generalUtils.extractJWT(request)
        //TODO ( "Implement validation JWT")
        val code = token

        studentService.changePassword(code, student)

        return ResponseEntity(MessageDto("Password changed successfully"), HttpStatus.OK)
    }
}