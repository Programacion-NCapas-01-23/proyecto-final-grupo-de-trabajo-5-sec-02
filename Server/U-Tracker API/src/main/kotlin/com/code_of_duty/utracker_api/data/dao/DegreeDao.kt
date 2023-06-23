package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface DegreeDao : ListCrudRepository<Degree, UUID> {

    @Query("SELECT d.id FROM Degree d WHERE d.name = :name")
    fun getDegreeUUIDByName(@Param("name") name: String): UUID

}