package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.StudentCycle
import org.springframework.data.jpa.repository.JpaRepository

interface StudentCycleDao : JpaRepository<StudentCycle, String> {
    fun findByStudentCode(studentCode: String): List<StudentCycle>
}
