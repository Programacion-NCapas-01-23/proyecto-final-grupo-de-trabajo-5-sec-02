package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.services.admin.cycle.AdminCycleService
import com.code_of_duty.utracker_api.services.api.cycle.CycleService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import com.code_of_duty.utracker_api.utils.UnauthorizedException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("\${api.base-path}/cycle")
@Tag(name = "API")
class CycleController(
    private val cycleService: CycleService,
    private val adminCycleService: AdminCycleService,
    private val generalUtils: GeneralUtils
) {


    @Operation(
        summary = "Get all cycles",
        description = "Get all cycles based on your degree",
        security = [SecurityRequirement(name = "ApiAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles successfully retrieved",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Cycle::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/")
    fun getAllCycles(
        @RequestParam(required = false) id: String?,
        request: HttpServletRequest
    ): ResponseEntity<List<Cycle>> {
        return try {
            val isAdmin = generalUtils.extractJWT(request).toBoolean()

            if (isAdmin) {
                // Handle admin user logic
                // Here, you can return all cycles or perform any specific admin-related tasks
                // Example:
                val cycles = adminCycleService.getAllCycles() ?: emptyList()
                ResponseEntity.ok(cycles)
            } else if (id != null) {
                // Handle regular user logic with the provided id parameter
                val uuid = UUID.fromString(id)
                val cycles = cycleService.getAllCycles(uuid) ?: emptyList()
                ResponseEntity.ok(cycles)
            } else {
                // Handle regular user logic without the id parameter (using token)
                val userDegree = generalUtils.extractUserDegreeFromToken(request)
                    ?: throw UnauthorizedException("User degree not found in token")

                val cycles = cycleService.getAllCycles(userDegree) ?: emptyList()
                ResponseEntity.ok(cycles)
            }
        } catch (e: UnauthorizedException) {
            // Handle unauthorized access
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (e: Exception) {
            // Return an error response
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }


    @GetMapping("/getCycleByStudent")
    @SecurityRequirement(name = "ApiAuth")
    fun getCycleByStudent(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/createCycle")
    @SecurityRequirement(name = "ApiAuth")
    fun createCycle(
        request: HttpServletRequest,
        @RequestBody body: Any//TODO: Add CycleRequest
    ): ResponseEntity<Any> {
        TODO()
    }

    @PostMapping("/addSubject")
    @SecurityRequirement(name = "ApiAuth")
    fun addSubject(
        request: HttpServletRequest,
        @RequestBody body: Any//TODO: Add SubjectRequest
    ): ResponseEntity<Any> {
        TODO()
    }
}