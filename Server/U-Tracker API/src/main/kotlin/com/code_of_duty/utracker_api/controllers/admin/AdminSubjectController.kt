package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Subject
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
@RequestMapping("\${admin.base-path}/subjects")
@Tag(name = "Admin")
class AdminSubjectController {
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subjects: List<Subject>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subjects: List<Subject>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subject: Subject
    ): ResponseEntity<MessageDto> {
        TODO()
    }
}