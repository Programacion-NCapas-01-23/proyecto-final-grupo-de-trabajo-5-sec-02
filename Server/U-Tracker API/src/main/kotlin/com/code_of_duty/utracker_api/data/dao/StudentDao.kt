package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentDao : JpaRepository<Student, Long> {
    fun findByCode(code: String): Student?
    fun existsByCode(code: String): Boolean
    fun save(student: Student): Student
}