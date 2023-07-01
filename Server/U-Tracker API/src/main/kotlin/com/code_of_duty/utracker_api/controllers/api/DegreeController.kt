package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.services.api.degree.DegreeService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("\${api.base-path}/degree")
@Tag(name = "API")
class DegreeController(
    private val degreeService: DegreeService
) {
    @GetMapping("/")
    fun getAllDegrees(
        @RequestParam(required = true) id: UUID,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val degrees = degreeService.getAllDegrees(id)
        return ResponseEntity.ok(degrees)
    }
}