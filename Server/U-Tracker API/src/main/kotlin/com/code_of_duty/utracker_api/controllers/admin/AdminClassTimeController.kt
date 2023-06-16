package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.ClassTimeDTO
import com.code_of_duty.utracker_api.data.dtos.ClassTimeUpdateDto
import com.code_of_duty.utracker_api.data.dtos.ErrorsDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.ClassTime
import com.code_of_duty.utracker_api.services.admin.auth.AdminAuthService
import com.code_of_duty.utracker_api.services.admin.classTime.ClassTimeService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("\${admin.base-path}/class-time")
@Tag(name = "Admin")
class AdminClassTimeController(
    private val classTimeService: ClassTimeService,
    private val generalUtils: GeneralUtils,
) {
    //region add_Doc
    @Operation(
        summary = "Add class time",
        description = "Add classes times to the system, each class time is a day and a start time and end time",
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
                responseCode = "409",
                description = "Invalid class time",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PostMapping("/add")
    fun addClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classTimes: List<ClassTimeDTO>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        classTimeService.addClassTime(classTimes)
        return ResponseEntity(MessageDto("Class time added successfully"), HttpStatus.OK)
    }

    //region delete_Doc
    @Operation(
        summary = "Delete class time",
        description = "Delete class time from the system",
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
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @DeleteMapping("/delete")
    fun deleteClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classesTimes: List<UUID>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        classTimeService.deleteClassTime(classesTimes)
        return ResponseEntity(MessageDto("Class time deleted successfully"), HttpStatus.OK)
    }

    //region update_Doc
    @Operation(
        summary = "Update class time",
        description = "Update class time in the system",
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
                responseCode = "409",
                description = "Invalid class time",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PatchMapping("/update")
    fun updateClassTime(
        request: HttpServletRequest,
        @Valid @RequestBody classTime: ClassTimeUpdateDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)

        classTimeService.updateClassTime(classTime)

        return ResponseEntity(MessageDto("Class time updated successfully"), HttpStatus.OK)
    }
}