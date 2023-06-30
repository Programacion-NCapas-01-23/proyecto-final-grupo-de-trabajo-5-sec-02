package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.ErrorsDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.dtos.SubjectXPrerequisiteDto
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.services.admin.subject.AdminSubjectService
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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/subjects")
@Tag(name = "Admin")
class AdminSubjectController(
    private val generalUtils: GeneralUtils,
    private val adminSubjectService: AdminSubjectService
) {
    @GetMapping("/getAllSubjects")
    fun getAllSubjects(
        @RequestParam(name = "nameFilter", required = false) nameFilter: String?,
        @RequestParam(name = "sortBy", required = false) sortBy: String?,
        @RequestParam(name = "degreeFilter", required = false) degreeFilter: String?,
        @RequestParam(name = "pensumFilter", required = false) pensumFilter: String?,
        @RequestParam(name = "facultyFilter", required = false) facultyFilter: String?
    ): List<SubjectDto> {
        return adminSubjectService.getAllSubjects(nameFilter, sortBy, degreeFilter, pensumFilter, facultyFilter)
    }
    //region Add_docs
    @Operation(
        summary = "Add subjects",
        description = "Add subjects to the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subjects added successfully",
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
            )
        ]
    )
    //endregion
    @PostMapping("/add")
    fun addSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subjects: List<SubjectDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminSubjectService.addAllSubjects(subjects)
        return ResponseEntity(
            MessageDto("Subjects added successfully"),
            HttpStatus.OK
        )
    }

    //region Delete_docs
    @Operation(
        summary = "Delete subjects",
        description = "Delete subjects from the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subjects deleted successfully",
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
    fun deleteSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subjects: List<String>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminSubjectService.deleteAllListedSubjects(subjects)
        return ResponseEntity(
            MessageDto("Subjects deleted successfully"),
            HttpStatus.OK
        )
    }

    //region Update_docs
    @Operation(
        summary = "Update subjects",
        description = "Update subjects in the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subjects updated successfully",
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
            )
        ]
    )
    //endregion
    @PatchMapping("/update")
    @SecurityRequirement(name = "AdminAuth")
    fun updateSubject(
        request: HttpServletRequest,
        @Valid @RequestBody subject: SubjectDto
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminSubjectService.updateSubject(subject)
        return ResponseEntity(
            MessageDto("Subject updated successfully"),
            HttpStatus.OK
        )
    }

    //region Delete_all_docs
    @Operation(
        summary = "Delete all subjects",
        description = "Delete all subjects from the database",
        security = [SecurityRequirement(name = "AdminAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Subjects deleted successfully",
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
    @DeleteMapping("/delete/all")
    @SecurityRequirement(name = "AdminAuth")
    fun deleteAllSubjects(
        request: HttpServletRequest
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminSubjectService.deleteAllSubjects()
        return ResponseEntity(
            MessageDto("Subjects deleted successfully"),
            HttpStatus.OK
        )
    }

    @PostMapping("/addPrerequisite")
    @SecurityRequirement(name = "AdminAuth")
    fun addPrerequisite(
        request: HttpServletRequest,
        @Valid @RequestBody subjects: List<SubjectXPrerequisiteDto>
    ): ResponseEntity<MessageDto> {
        generalUtils.extractJWT(request)
        adminSubjectService.addPrerequisites(subjects)
        return ResponseEntity(
            MessageDto("Subject updated successfully"),
            HttpStatus.OK
        )
    }
}