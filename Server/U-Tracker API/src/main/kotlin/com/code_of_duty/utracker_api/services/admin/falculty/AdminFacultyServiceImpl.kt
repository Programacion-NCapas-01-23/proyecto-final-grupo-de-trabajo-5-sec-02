package com.code_of_duty.utracker_api.services.admin.falculty

import com.code_of_duty.utracker_api.data.dao.FacultyDao
import com.code_of_duty.utracker_api.data.dtos.FacultyDto
import com.code_of_duty.utracker_api.data.models.Faculty
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminFacultyServiceImpl(
    private val facultyDao: FacultyDao
) : AdminFacultyService {
    override fun addAllFaculties(faculties: List<FacultyDto>) {
        faculties.forEach {
            val faculty = Faculty(
                name = it.name,
                description = it.description,
                logo = it.logo ?: ""
            )
            facultyDao.save(faculty)
        }
    }

    override fun deleteFaculties(faculties: List<String>) {
        faculties.forEach{
            val faculty = facultyDao.findById(UUID.fromString(it)).orElseThrow { ExceptionNotFound("Faculty not found") }
            facultyDao.delete(faculty)
        }
    }

    override fun updateFaculty(faculty: Faculty) {
        facultyDao.findById(faculty.id).orElseThrow { ExceptionNotFound("Faculty not found") }
        facultyDao.save(faculty)
    }
}