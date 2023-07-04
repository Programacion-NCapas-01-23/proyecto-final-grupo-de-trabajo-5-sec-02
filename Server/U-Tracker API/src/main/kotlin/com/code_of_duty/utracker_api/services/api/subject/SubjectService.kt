package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dtos.RemainingAssessmentDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.Subject
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
interface SubjectService {

    fun getAllSubjects(nameFilter: String?, sortBy: String?, degreeFilter: String?, pensumFilter: String?, facultyFilter: String?): List<SubjectDto>

    fun setAssessment(subjectCode: String, name: String, percentage: Int, date: LocalDate, grade: BigDecimal?): Subject

    fun calculateEstimateGrades(subjectCode: String): List<RemainingAssessmentDto>

    fun updateSubjectCompletion(studentCode: String, subjectCode: String, state: SubjectStatus, grade: BigDecimal?): Subject
}