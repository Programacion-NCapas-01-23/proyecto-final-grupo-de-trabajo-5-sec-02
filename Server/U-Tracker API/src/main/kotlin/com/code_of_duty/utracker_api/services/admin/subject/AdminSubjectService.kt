package com.code_of_duty.utracker_api.services.admin.subject

import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import org.springframework.stereotype.Service

@Service
interface AdminSubjectService {
    fun getAllSubjects(nameFilter: String?, sortBy: String?, degreeFilter: String?, pensumFilter: String?, facultyFilter: String?): List<SubjectDto>
    fun addAllSubjects(subjects: List<SubjectDto>)
    fun deleteAllListedSubjects(subjects: List<String>)
    fun updateSubject(subject: SubjectDto)
    fun deleteAllSubjects()
}