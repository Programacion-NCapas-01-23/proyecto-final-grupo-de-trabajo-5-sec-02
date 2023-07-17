package com.code_of_duty.utracker_api.services.admin.falculty

import com.code_of_duty.utracker_api.data.dtos.FacultyDto
import com.code_of_duty.utracker_api.data.models.Faculty
import org.springframework.stereotype.Service

@Service
interface AdminFacultyService {
    fun getAllFaculties(): List<Faculty>
    fun addAllFaculties(faculties: List<FacultyDto>)
    fun deleteFaculties(faculties: List<String>)
    fun updateFaculty(faculty: Faculty)
}