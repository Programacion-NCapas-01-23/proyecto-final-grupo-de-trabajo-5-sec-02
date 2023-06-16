package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.enums.CycleType
import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.data.models.Pensum
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CycleDao : JpaRepository<Cycle, UUID> {

    fun findByNameAndPensum(name: String, pensum: Pensum): Cycle?
}