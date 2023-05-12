package com.code_of_duty.utracker_api.controllers

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.dtos.StudentResponseDto
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @PatchMapping("/updateStudent")
    @Operation(summary = "Update student Information")
    fun updateStudent(
        @RequestHeader("Authorization") jwt: String,
        @Parameter(description = "Student information to update")
        @RequestBody student: StudentRequestDto
    ): ResponseEntity<MessageDto> {
        //TODO(): Implement all validations for student and JWT
        return ResponseEntity(MessageDto("Student updated successfully"), HttpStatus.OK)
    }
}