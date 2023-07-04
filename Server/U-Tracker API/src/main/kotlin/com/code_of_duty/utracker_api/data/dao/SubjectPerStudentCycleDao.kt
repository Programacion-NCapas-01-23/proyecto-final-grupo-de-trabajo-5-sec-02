package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.StudentCycle
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SubjectPerStudentCycleDao : JpaRepository<SubjectPerStudentCycle, UUID> {
    @Query("SELECT s FROM SubjectPerStudentCycle s WHERE s.studentCycle.studentCycleId = :studentCycleId AND s.subject.code = :subjectCode")
    fun findByStudentCycleAndSubjectCode(
        @Param("studentCycleId") studentCycleId: UUID,
        @Param("subjectCode") subjectCode: String
    ): SubjectPerStudentCycle?
    @Query("SELECT s FROM SubjectPerStudentCycle s JOIN FETCH s.subject WHERE s.studentCycle.studentCycleId = :studentId")
    fun getSubjectStatusesForStudent(studentId: String): List<SubjectPerStudentCycle>
    fun findBySubjectCodeAndStudentCycle(subjectCode: String, studentCycle: StudentCycle): SubjectPerStudentCycle?
    fun findByStudentCycleAndSubject(studentCycle: StudentCycle, subject: Subject): SubjectPerStudentCycle?
    fun findBySubjectCode(subjectCode: String): List<SubjectPerStudentCycle>
    fun getEstimateGradeBySubjectCode(subjectCode: String): Double?

}
