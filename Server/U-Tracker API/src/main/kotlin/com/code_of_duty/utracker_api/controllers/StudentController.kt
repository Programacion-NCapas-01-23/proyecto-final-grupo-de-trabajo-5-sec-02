package com.code_of_duty.utracker_api.controllers

import com.code_of_duty.utracker_api.data.dtos.GetStudentDto
import com.code_of_duty.utracker_api.data.dtos.StudentResponseDto
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {
    @GetMapping("/getStudent")
    @Operation(summary = "Get student by code")
    fun getStudent(
        //TODO (): Agregar jwt en header y dejar sin body el endpoint
        @Parameter(name = "getStudentDto", required = true, description = "Student code and JWT")
        @RequestBody @Valid dto: GetStudentDto,
        result: BindingResult
    ): ResponseEntity<StudentResponseDto> {
        //* verificar por que no aplica las validaciones
        if (result.hasErrors()) {
            print("errores")
        }
        //TODO ( "Implement validation JWT")
        val student = studentService.getStudent(dto.code) ?: throw StudentNotFoundException("Student with code ${dto.code} not found")
        val response = StudentResponseDto(
            code = student.code,
            username = student.username,
            image = student.image,
            cum = student.cum,
            degree = student.degree
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}