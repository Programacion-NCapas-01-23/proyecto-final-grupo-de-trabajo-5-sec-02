package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerCycle
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository

interface SubjectDao : JpaRepository<Subject, String> {
    @EntityGraph(attributePaths = ["subjectPerCycles"])
    fun findByCode(code: String): Subject?
    fun findByName(name: String): Subject?

    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.subjectPerCycles")
    fun findAllWithSubjectPerCycles(): List<Subject>

    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.subjectPerCycles WHERE s.code = :code")
    fun findByCodeWithSubjectPerCycles(code: String): Subject?

}