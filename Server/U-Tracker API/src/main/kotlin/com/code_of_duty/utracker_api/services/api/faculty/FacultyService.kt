package com.code_of_duty.utracker_api.services.api.faculty

import com.code_of_duty.utracker_api.data.dtos.StudentFacultyDto
import com.code_of_duty.utracker_api.data.models.Faculty
import org.springframework.stereotype.Service


@Service
interface FacultyService {

    fun getAllFaculties(): List<StudentFacultyDto>
}