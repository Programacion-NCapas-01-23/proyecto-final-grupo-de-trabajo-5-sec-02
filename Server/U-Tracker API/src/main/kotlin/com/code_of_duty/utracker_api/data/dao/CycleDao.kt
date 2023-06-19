package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.data.models.Degree
import com.code_of_duty.utracker_api.data.models.Pensum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CycleDao : JpaRepository<Cycle, UUID> {
    fun findByNameAndPensum(name: String, pensum: Pensum): Cycle?

    fun findByFaculty(@Param("faculty") faculty: String): List<Cycle>
}