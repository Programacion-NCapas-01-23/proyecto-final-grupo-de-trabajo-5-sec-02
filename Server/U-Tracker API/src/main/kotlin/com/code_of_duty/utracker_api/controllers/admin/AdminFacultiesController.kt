package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.FacultyDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.models.Faculty
import com.code_of_duty.utracker_api.services.admin.falculty.AdminFacultyService
import com.code_of_duty.utracker_api.utils.GeneralUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/faculties")
@Tag(name = "Admin")
class AdminFacultiesController(
    private val generalUtils: GeneralUtils,
) {
    @Autowired
    lateinit var adminFacultyService: AdminFacultyService

    @GetMapping("/all")
    fun getAllFaculties(): ResponseEntity<List<Faculty>> {
        return ResponseEntity(
            adminFacultyService.getAllFaculties(),
            HttpStatus.OK
        )
    }

    //region addFaculty_doc
    @Operation(
        summary = "Add a new faculty",
        description = "Add a new faculty to the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Faculty added successfully",
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
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PostMapping("/add")
    fun addFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculties: List<FacultyDto>
    ): ResponseEntity<MessageDto>{
        generalUtils.extractJWT(request)
        adminFacultyService.addAllFaculties(faculties)
        return ResponseEntity(
            MessageDto("Faculties added successfully"),
            HttpStatus.OK
        )
    }

    //region deleteFaculty_doc
    @Operation(
        summary = "Delete a faculty",
        description = "Delete a faculty from the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Faculty deleted successfully",
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
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @DeleteMapping("/delete")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculties: List<String>
        ): ResponseEntity<MessageDto>{
        generalUtils.extractJWT(request)
        adminFacultyService.deleteFaculties(faculties)
        return ResponseEntity(
            MessageDto("Faculties deleted successfully"),
            HttpStatus.OK
        )
    }

    //region updateFaculty_docs
    @Operation(
        summary = "update faculty",
        description = "update data in database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "update faculty successfully",
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
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
    //endregion
    @PatchMapping("/update")
    fun updateFaculty(
        request: HttpServletRequest,
        @Valid @RequestBody faculty: Faculty
    ): ResponseEntity<MessageDto>{
        generalUtils.extractJWT(request)
        adminFacultyService.updateFaculty(faculty)
        return ResponseEntity(
            MessageDto("Faculty updated successful"),
            HttpStatus.OK
        )
    }
}