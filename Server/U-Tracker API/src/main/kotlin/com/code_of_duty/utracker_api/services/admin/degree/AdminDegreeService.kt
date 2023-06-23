package com.code_of_duty.utracker_api.services.admin.degree

import com.code_of_duty.utracker_api.data.dtos.DegreeDto
import com.code_of_duty.utracker_api.data.dtos.UpdateDegreeDto
import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.stereotype.Service

@Service
interface AdminDegreeService {
    fun getAllDegrees(): List<Degree>
    fun addAllDegrees(degrees: List<DegreeDto>)
    fun deleteAllDegrees(degrees: List<String>)
    fun updateDegree(degree: UpdateDegreeDto)
}