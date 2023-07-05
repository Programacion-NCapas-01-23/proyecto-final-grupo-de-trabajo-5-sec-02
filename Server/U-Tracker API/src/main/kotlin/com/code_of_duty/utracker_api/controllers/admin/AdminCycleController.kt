package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.services.admin.cycle.AdminCycleService
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
@RequestMapping("\${admin.base-path}/cycles")
@Tag(name = "Admin")
class AdminCycleController(
    private val adminCycleService: AdminCycleService,
    private val generalUtils: GeneralUtils
) {
    @Operation(
        summary = "Get all cycles",
        description = "Get all cycles",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = Cycle::class
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
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
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
    @GetMapping("/all")
    fun getAllCycles(): ResponseEntity<List<Cycle>> {
        return ResponseEntity(
            adminCycleService.getAllCycles(),
            HttpStatus.OK
        )
    }
    //region add_docs
    @Operation(
        summary = "Add cycles",
        description = "Add a list of cycles",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles added successfully",
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
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
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
    fun addCycle(
        request: HttpServletRequest,
        @Valid @RequestBody cycles: List<CycleDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminCycleService.addAllCycles(cycles)
        return ResponseEntity(
            MessageDto("Cycles added successfully"),
            HttpStatus.OK
        )
    }

    //region delete_docs
    @Operation(
        summary = "Delete cycles",
        description = "Delete a list of cycles",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycles deleted successfully",
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
                description = "Bad Request",
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
    fun deleteCycle(
        request: HttpServletRequest,
        @Valid @RequestBody cycles: List<String>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminCycleService.deleteAllCycles(cycles)
        return ResponseEntity(
            MessageDto("Cycles deleted successfully"),
            HttpStatus.OK
        )
    }

    //region update_docs
    @Operation(
        summary = "Update a cycle",
        description = "Update a cycle",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cycle updated successfully",
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
                description = "Bad Request",
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
    fun updateCycle(
        request: HttpServletRequest,
        @Valid @RequestBody cycle: CycleDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminCycleService.updateCycle(cycle)
        return ResponseEntity(
            MessageDto("Cycle updated successfully"),
            HttpStatus.OK
        )
    }
}