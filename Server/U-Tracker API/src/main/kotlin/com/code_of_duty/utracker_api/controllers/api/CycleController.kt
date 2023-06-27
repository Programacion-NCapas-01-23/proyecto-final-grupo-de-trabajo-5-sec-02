package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.services.api.cycle.CycleService
import com.code_of_duty.utracker_api.services.api.subject.SubjectService
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import com.code_of_duty.utracker_api.utils.GeneralUtils
import com.code_of_duty.utracker_api.utils.UnauthorizedException
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
    private val subjectService: SubjectService,
    private val generalUtils: GeneralUtils
) {
    @GetMapping("/")
    fun getAllCycles(
        @RequestParam(required = false) id: UUID?,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return try {
            val studentCode = generalUtils.extractJWT(request)
            val cycles = cycleService.getAllCycles(studentCode)
            ResponseEntity.ok(cycles)
        } catch (e: UnauthorizedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
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
    fun createStudentCycle(
        request: HttpServletRequest,
        @RequestBody body: StudentCycleDto
    ): ResponseEntity<Any> {
        return try {
            val studentCode = generalUtils.extractJWT(request)
            cycleService.createStudentCycle(
                studentCode = studentCode,
                cycleType = body.cycleType,
                year = body.year
            )
            ResponseEntity.ok().build()
        } catch (e: UnauthorizedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (e: ExceptionNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
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