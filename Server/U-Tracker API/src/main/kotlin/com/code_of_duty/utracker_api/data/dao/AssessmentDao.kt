package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface AssessmentDao: ListCrudRepository<Assessment, UUID> {
    fun save(assessmentDto: Assessment)

    fun findBySubjectPerStudentCycle(subjectPerStudentCycle: SubjectPerStudentCycle): List<Assessment>

    @Query("SELECT a FROM Assessment a WHERE a.id = :assessmentId AND a.subjectPerStudentCycle.studentCycle.student.code = :studentCode")
    fun findByIdAndStudentCode(assessmentId: UUID, studentCode: String): Assessment
}