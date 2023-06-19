package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Subject
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository

interface SubjectDao : ListCrudRepository<Subject, String> {
    fun findByCode(code: String): List<Subject>
    fun findByName(name: String): Subject?

    @Query("SELECT DISTINCT s FROM Subject s LEFT JOIN FETCH s.subjectPerCycles")
    fun findAllWithSubjectPerCycles(): List<Subject>

}