package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.DegreeDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Degree
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
@RequestMapping("\${admin.base-path}/degrees")
@Tag(name = "Admin")
class AdminDegreesController {
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degrees: List<DegreeDto>
    ): ResponseEntity<MessageDto> {
        TODO("Implementar este método")
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degrees: List<Degree>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degree: Degree
    ): ResponseEntity<MessageDto> {
        TODO()
    }
}