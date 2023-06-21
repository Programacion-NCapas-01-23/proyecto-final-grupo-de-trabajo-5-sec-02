package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DegreeDao : JpaRepository<Degree, UUID> {

    fun findByName(name: String): Degree?

    fun existsByName(name: String): Boolean

}