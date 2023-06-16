package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Assessment
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface AssesmentDao: ListCrudRepository<Assessment, UUID> {
    //fun saveAssesment(assesmentDto: Assessment)
    //fun findBySubjectId(code: String): List<Assessment>
}