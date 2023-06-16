package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.ErrorsDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.SchedulesDto
import com.code_of_duty.utracker_api.services.admin.schedule.AdminScheduleService
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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${admin.base-path}/schedules")
@Tag(name = "Admin")
class AdminSchedulesController(
    private val adminScheduleService: AdminScheduleService,
    private val generalUtils: GeneralUtils
) {
    //region add_docs
    @Operation(
        summary = "Add schedules",
        description = "Add schedules to the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Schedules added successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorsDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    //endregion
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedules: List<SchedulesDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminScheduleService.addAllSchedules(schedules)
        return ResponseEntity(
            MessageDto("Schedules added successfully"),
            HttpStatus.OK
        )
    }

    //region delete_docs
    @Operation(
        summary = "Delete schedules",
        description = "Delete schedules from the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Schedules deleted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorsDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    //endregion
    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedules: List<String>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminScheduleService.deleteAllListedSchedules(schedules)
        return ResponseEntity(
            MessageDto("Schedules deleted successfully"),
            HttpStatus.OK
        )
    }

    //region update_docs
    @Operation(
        summary = "Update schedules",
        description = "Update schedules in the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Schedules updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorsDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    //endregion
    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedule: SchedulesDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminScheduleService.updateSchedule(schedule)
        return ResponseEntity(
            MessageDto("Schedule updated successfully"),
            HttpStatus.OK
        )
    }

    //region delete_all_docs
    @Operation(
        summary = "Delete all schedules",
        description = "Delete all schedules from the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "All schedules deleted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorsDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = MessageDto::class
                        )
                    )
                ]
            )
        ]
    )
    //endregion
    @DeleteMapping("/delete/all")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteAllSchedules(
        request: HttpServletRequest
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminScheduleService.deleteAllSchedules()
        return ResponseEntity(
            MessageDto("All schedules deleted successfully"),
            HttpStatus.OK
        )
    }
}