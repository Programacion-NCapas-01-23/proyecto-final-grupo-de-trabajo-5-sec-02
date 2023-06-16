package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.ErrorsDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.PensumDto
import com.code_of_duty.utracker_api.data.models.Pensum
import com.code_of_duty.utracker_api.services.admin.pensum.AdminPensumService
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

@RestController
@RequestMapping("\${admin.base-path}/pensum")
@Tag(name = "Admin")
class AdminPensumController(
    private val generalUtils: GeneralUtils,
    private val adminPensumService: AdminPensumService
) {
    //region add_doc
    @Operation(
        summary = "new Pensum",
        description = "add new List of Pensum",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "add successful",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PostMapping("/add")
    fun addPensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: List<PensumDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminPensumService.addAllPensums(pensum)
        return ResponseEntity(
            MessageDto(
                "Pensum added successfully"
            ),
            HttpStatus.OK
        )
    }

    //region delete_doc
    @Operation(
        summary = "delete Pensum",
        description = "delete List of Pensum",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "delete successful",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @DeleteMapping("/delete")
    fun deletePensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: List<String>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminPensumService.deleteAllListedPensums(pensum)
        return ResponseEntity(
            MessageDto(
                "Pensum deleted successfully"
            ),
            HttpStatus.OK
        )
    }

    //region update_doc
    @Operation(
        summary = "update Pensum",
        description = "update Pensum",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "update successful",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updatePensum(
        request: HttpServletRequest,
        @Valid @RequestBody pensum: PensumDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminPensumService.updatePensum(pensum)
        return ResponseEntity(
            MessageDto(
                "Pensum updated successfully"
            ),
            HttpStatus.OK
        )
    }
}