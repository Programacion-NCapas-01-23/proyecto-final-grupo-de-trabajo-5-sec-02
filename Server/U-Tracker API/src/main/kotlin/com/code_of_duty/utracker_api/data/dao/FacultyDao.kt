package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Faculty
import org.springframework.data.repository.ListCrudRepository
import java.util.UUID

interface FacultyDao : ListCrudRepository<Faculty, UUID> {
    fun existsByName(name: String): Boolean
}