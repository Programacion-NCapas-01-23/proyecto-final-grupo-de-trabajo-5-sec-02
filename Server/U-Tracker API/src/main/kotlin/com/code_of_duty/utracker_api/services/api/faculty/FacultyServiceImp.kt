package com.code_of_duty.utracker_api.services.api.faculty

import com.code_of_duty.utracker_api.data.dao.FacultyDao
import com.code_of_duty.utracker_api.data.dtos.StudentFacultyDto
import com.code_of_duty.utracker_api.data.models.Faculty
import org.springframework.stereotype.Component
import java.util.*

@Component
class FacultyServiceImp(
    private val facultyDao: FacultyDao
) : FacultyService {
    override fun getAllFaculties(): List<StudentFacultyDto> {
        return facultyDao.findAll().map {
            StudentFacultyDto(
                id = it.id.toString(),
                name = it.name
            )
        }
    }
}