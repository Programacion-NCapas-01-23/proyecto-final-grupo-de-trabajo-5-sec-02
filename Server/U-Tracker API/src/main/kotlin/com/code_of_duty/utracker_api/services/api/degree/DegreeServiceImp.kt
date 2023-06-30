package com.code_of_duty.utracker_api.services.api.degree

import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.dtos.StudentDegreeDto
import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.stereotype.Component
import java.util.*

@Component
class DegreeServiceImp (private val degreeDao: DegreeDao) : DegreeService {
    override fun getAllDegrees(facultyId: UUID): List<StudentDegreeDto> {
        val degrees: List<Degree> = degreeDao.findAllByFacultyId(facultyId)
        return degrees.map { degree ->
            StudentDegreeDto(
                facultyId = degree.faculty?.id.toString(),
                id = degree.id.toString(),
                name = degree.name
            )
        }
    }

    override fun findById(id: UUID): Degree = degreeDao.findById(id).orElse(null)
}