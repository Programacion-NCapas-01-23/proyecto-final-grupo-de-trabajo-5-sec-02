package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerCycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SubjectPerCycleDao: JpaRepository<SubjectPerCycle, UUID> {
    fun findAllBySubject(subject: Subject): Iterable<SubjectPerCycle>
    @Query("SELECT s FROM SubjectPerCycle s WHERE s.subject.code = :subjectCode")
    fun findBySubject(subjectCode: String): SubjectPerCycle?

    fun findByCorrelativeAndCycleAndSubject(correlative: Int, cycle: Cycle, subject: Subject): SubjectPerCycle?
}