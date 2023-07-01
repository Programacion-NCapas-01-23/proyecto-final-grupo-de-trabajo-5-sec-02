package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dtos.AssesmentDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import org.springframework.stereotype.Service

@Service
interface SubjectService {

    fun getAllSubjects(nameFilter: String?, sortBy: String?, degreeFilter: String?, pensumFilter: String?, facultyFilter: String?): List<SubjectDto>

    fun setAssessment(subjectCode: String, assessmentDto: AssesmentDto): Subject

    fun calculateEstimateGrades(code: String, assessment: List<Assessment>): List<Double>

    fun updateSubjectCompletion(studentCode: String, subjectCode: String, completed: Boolean, grade: Float?): Subject
}