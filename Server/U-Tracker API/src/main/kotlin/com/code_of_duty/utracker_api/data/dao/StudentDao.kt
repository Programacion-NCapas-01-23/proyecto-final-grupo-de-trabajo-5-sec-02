package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.data.repository.ListCrudRepository

interface StudentDao : ListCrudRepository<Student, String> {
    fun existsByCode(code: String): Boolean
    fun save(student: Student): Student
    fun findByEmail(email: String): Student?
    fun findByUsername(code: String): Student?
}