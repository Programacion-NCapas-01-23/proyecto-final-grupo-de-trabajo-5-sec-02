package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.Subject
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Service
interface SubjectService {

    fun getAllSubjects(nameFilter: String?, sortBy: String?, degreeFilter: String?, pensumFilter: String?, facultyFilter: String?): List<SubjectDto>

    fun getAllAssessments(studentCode: String, subjectCode: String): List<AssessmentResponseDto>
    fun setAssessment(studentCode:String, subjectCode: String, name: String, percentage: Int, date: LocalDate, grade: BigDecimal?): Subject
    fun updateAssessment(studentCode:String, assessmentId: UUID, name: String?, percentage: Int?, date: LocalDate?, grade: BigDecimal?): Subject
    fun deleteAssessment(studentCode: String, assessmentId: UUID): Subject

    fun calculateEstimateGrades(studentCode: String, subjectCode: String): List<RemainingAssessmentDto>

    fun updateSubjectCompletion(studentCode: String, subjectCode: String, state: SubjectStatus, grade: BigDecimal?): Subject

    fun calculateRemainingGradeToPass(studentCode: String, subjectCode: String): PassGradeDto

    fun calculateCum(studentCode: String): CumDto
}