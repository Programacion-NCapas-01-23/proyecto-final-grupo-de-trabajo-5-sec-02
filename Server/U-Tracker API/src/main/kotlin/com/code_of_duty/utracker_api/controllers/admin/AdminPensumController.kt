package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Pensum
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
@RequestMapping("\${admin.base-path}/pensum")
@Tag(name = "Admin")
class AdminPensumController {
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addPensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: List<Pensum>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deletePensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: List<Pensum>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updatePensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: Pensum
    ): ResponseEntity<MessageDto> {
        TODO()
    }
}