package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.dtos.StudentDegreeDto
import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface DegreeDao : ListCrudRepository<Degree, UUID> {

    fun findAllByFacultyId(facultyId: UUID): List<Degree>

    fun existsByName(name: String): Boolean

}