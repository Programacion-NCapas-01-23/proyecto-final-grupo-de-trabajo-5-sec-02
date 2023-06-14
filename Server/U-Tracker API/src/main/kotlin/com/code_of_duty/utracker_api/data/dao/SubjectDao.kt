package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Subject
import org.hibernate.validator.constraints.UUID
import org.springframework.data.repository.ListCrudRepository



interface SubjectDao : ListCrudRepository<Subject, UUID> {
    fun findByCode(code: String): List<Subject>
    fun findByName(name: String): Subject?
    //fun findBySt(studentId: String): List<Subject>
}