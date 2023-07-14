package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository

interface StudentDao : ListCrudRepository<Student, String> {

    fun findByCode(code: String): Student?
    fun existsByCode(code: String): Boolean
    fun save(student: Student): Student
    fun findByEmail(email: String): Student?
    fun findByUsername(code: String): Student?

    @Query("SELECT COUNT(s) FROM SubjectPerStudentCycle s WHERE s.studentCycle.student.code = :code AND s.status = 0")
    fun countApprovedSubjectsByStudentCode(code: String): Int

}