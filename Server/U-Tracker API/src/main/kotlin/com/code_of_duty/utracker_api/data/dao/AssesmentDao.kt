package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface AssesmentDao: ListCrudRepository<Assessment, UUID> {
    fun save(assesmentDto: Assessment)

    fun findBySubjectPerStudentCycle(subjectPerStudentCycle: SubjectPerStudentCycle): List<Assessment>
}