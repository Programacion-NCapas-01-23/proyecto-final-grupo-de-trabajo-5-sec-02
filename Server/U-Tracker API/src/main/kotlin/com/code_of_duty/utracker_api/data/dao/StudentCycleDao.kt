package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.StudentCycle
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentCycleDao : JpaRepository<StudentCycle, UUID> {
    fun findByStudentCode(studentCode: String): List<StudentCycle>
    fun findByStudentCodeAndCycleId(studentCode: String, cycleId: String): StudentCycle?
}
