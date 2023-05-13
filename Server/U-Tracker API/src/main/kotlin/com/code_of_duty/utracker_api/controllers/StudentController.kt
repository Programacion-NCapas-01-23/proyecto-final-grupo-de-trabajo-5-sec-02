package com.code_of_duty.utracker_api.controllers

import com.code_of_duty.utracker_api.data.dtos.StudentResponseDto
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {
    @GetMapping("/getStudent")
    @Operation(summary = "Get student by code")
    fun getStudent(
        @RequestHeader("Authorization") jwt: String,
    ): ResponseEntity<StudentResponseDto> {
        val code = jwt.split(" ")[1]
        //TODO ( "Implement validation JWT")
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
}