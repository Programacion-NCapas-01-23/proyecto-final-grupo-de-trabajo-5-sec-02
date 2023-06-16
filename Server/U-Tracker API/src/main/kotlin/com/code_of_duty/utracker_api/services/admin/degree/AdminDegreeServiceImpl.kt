package com.code_of_duty.utracker_api.services.admin.degree

import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.dao.FacultyDao
import com.code_of_duty.utracker_api.data.dtos.DegreeDto
import com.code_of_duty.utracker_api.data.dtos.UpdateDegreeDto
import com.code_of_duty.utracker_api.data.models.Degree
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminDegreeServiceImpl(
    private val facultyDao: FacultyDao,
    private val degreeDao: DegreeDao
) : AdminDegreeService {
    override fun addAllDegrees(degrees: List<DegreeDto>) {
        degrees.forEach {
            val faculty = facultyDao.findById(it.facultyId).orElseThrow {
                ExceptionNotFound("Faculty not found")
            }

            val degree = Degree(
                name = it.name,
                faculty = faculty
            )

            degreeDao.save(degree)
        }
    }

    override fun deleteAllDegrees(degrees: List<String>) {
        degrees.forEach {
            val degree = degreeDao.findById(UUID.fromString(it)).orElseThrow {
                ExceptionNotFound("Degree not found")
            }

            degreeDao.delete(degree)
        }
    }

    override fun updateDegree(degree: UpdateDegreeDto) {
        degreeDao.findById(UUID.fromString(degree.id))
            ?: throw ExceptionNotFound("Degree not found")

        val faculty = facultyDao.findById(UUID.fromString(degree.facultyId)).orElseThrow {
            ExceptionNotFound("Faculty not found")
        }

        val updateDegree = Degree(
            id = UUID.fromString(degree.id),
            name = degree.name,
            faculty = faculty
        )

        degreeDao.save(updateDegree)
    }
}