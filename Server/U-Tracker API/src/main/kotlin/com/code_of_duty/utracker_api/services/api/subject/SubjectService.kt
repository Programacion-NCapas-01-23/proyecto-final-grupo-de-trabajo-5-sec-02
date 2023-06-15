package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dtos.AssesmentDto
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import org.hibernate.validator.constraints.UUID
import org.springframework.stereotype.Service

@Service
interface SubjectService {

    fun getAllSubjects(code: String): List<Subject>

    fun getSubjectByName(name: String): Subject?

    fun getSubjectsByStudent(studentId: String): List<Subject>

    fun setAssessment(uuid: UUID, assessmentDto: AssesmentDto): Subject

    fun calculateEstimateGrades(code: String, assessment: List<Assessment>): Subject
}