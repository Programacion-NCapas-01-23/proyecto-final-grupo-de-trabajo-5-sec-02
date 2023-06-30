package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.StudentCycle
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SubjectPerStudentCycleDao : JpaRepository<SubjectPerStudentCycle, Long> {
    fun findByStudentCycleAndSubjectCode(studentCycleId: UUID, subjectCode: String): SubjectPerStudentCycle?

    fun findBySubjectCodeAndStudentCycle(subjectCode: String, studentCycleId: UUID): SubjectPerStudentCycle?

    @Query("SELECT s FROM SubjectPerStudentCycle s JOIN FETCH s.subject WHERE s.studentCycle.studentCycleId = :studentId")
    fun getSubjectStatusesForStudent(studentId: String): List<SubjectPerStudentCycle>

    fun findBySubjectCodeAndStudentCycle(subjectCode: String, studentCycle: StudentCycle): SubjectPerStudentCycle?

    fun findBySubjectCode(subjectCode: String): List<SubjectPerStudentCycle>
}
