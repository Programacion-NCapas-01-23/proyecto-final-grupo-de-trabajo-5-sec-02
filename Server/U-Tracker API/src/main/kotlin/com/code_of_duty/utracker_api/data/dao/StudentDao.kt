package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



interface StudentDao : JpaRepository<Student, Long> {

    fun existsByCode(code: String?): Boolean
}