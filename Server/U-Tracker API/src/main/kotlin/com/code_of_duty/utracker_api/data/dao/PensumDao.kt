package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Degree
import com.code_of_duty.utracker_api.data.models.Pensum
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PensumDao : JpaRepository<Pensum, UUID>{
    fun findByPlanAndDegree(plan: String, degree: Degree): Pensum?
}