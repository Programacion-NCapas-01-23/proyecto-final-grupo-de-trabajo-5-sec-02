package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.DegreeDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.UpdateDegreeDto
import com.code_of_duty.utracker_api.data.models.Degree
import com.code_of_duty.utracker_api.services.admin.degree.AdminDegreeService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/degrees")
@Tag(name = "Admin")
class AdminDegreesController(
    private val generalUtils: GeneralUtils
) {
    @Autowired
    lateinit var adminDegreeService: AdminDegreeService

    //region add_doc
    @Operation(
        summary = "new Degrees",
        description = "add new List of degrees",
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
            ),
        ]
    )
    //endregion
    @PostMapping("/add")
    fun addDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degrees: List<DegreeDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminDegreeService.addAllDegrees(degrees)
        return ResponseEntity(
            MessageDto("create successful"),
            HttpStatus.OK
        )
    }

    //region delete_doc
    @Operation(
        summary = "delete Degrees",
        description = "delete List of degrees",
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
            ),
        ]
    )
    //endregion
    @DeleteMapping("/delete")
    fun deleteDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degrees: List<String>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminDegreeService.deleteAllDegrees(degrees)
        return ResponseEntity(
            MessageDto("delete successful"),
            HttpStatus.OK
        )
    }
    //region update_doc
    @Operation(
        summary = "update Degree",
        description = "update Degree",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "update Successful",
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
            ),
        ]
    )
    //endregion
    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateDegree(
        request: HttpServletRequest,
        @Valid @RequestBody degree: UpdateDegreeDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminDegreeService.updateDegree(degree)
        return ResponseEntity(
            MessageDto("update Successful"),
            HttpStatus.OK
        )
    }
}