package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.FacultyDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Faculty
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/faculties")
@Tag(name = "Admin")
class AdminFacultiesController {
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculties: List<FacultyDto>
        ): ResponseEntity<MessageDto>{
        TODO("Implementar este método")
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculties: List<Faculty>
        ): ResponseEntity<MessageDto>{
        TODO("Implementar este método")
    }

    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculty: Faculty
        ): ResponseEntity<MessageDto>{
        TODO("Implementar este método")
    }
}