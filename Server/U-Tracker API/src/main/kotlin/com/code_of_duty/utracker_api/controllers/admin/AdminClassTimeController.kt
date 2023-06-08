package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.ClassTimeDTO
import com.code_of_duty.utracker_api.data.dtos.ErrorsDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.ClassTime
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
@RequestMapping("\${admin.base-path}/class-time")
@Tag(name = "Admin")
class AdminClassTimeController {
    @Operation(
        summary = "Add class time",
        description = "Add class time",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Class time added successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
        ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/add")
    fun addClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classTimes: List<ClassTimeDTO>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @Operation(
        summary = "Delete class time",
        description = "Delete class time",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Class time deleted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/delete")
    fun deleteClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classTimes: List<ClassTime>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @Operation(
        summary = "Update class time",
        description = "Update class time",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Class time updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @PatchMapping("/update")
    fun updateClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classTime: ClassTime
    ): ResponseEntity<MessageDto> {
        TODO()
    }
}