package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.SchedulesDto
import com.code_of_duty.utracker_api.data.dtos.ScheduleswithIDDto
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${admin.base-path}/schedules")
@Tag(name = "Admin")
class AdminSchedulesController {
    @PostMapping("/add")
    @SecurityRequirement(name = "AdminAuth")
    fun addSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedules: List<SchedulesDto>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedules: List<SchedulesDto>
    ): ResponseEntity<MessageDto> {
        TODO()
    }

    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody schedule: ScheduleswithIDDto
    ): ResponseEntity<MessageDto> {
        TODO()
    }
}