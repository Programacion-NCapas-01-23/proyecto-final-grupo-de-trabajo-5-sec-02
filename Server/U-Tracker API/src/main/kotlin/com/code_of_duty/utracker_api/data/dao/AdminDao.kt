package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Admins
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface AdminDao: ListCrudRepository<Admins, UUID> {
    fun findByUsername(username: String): Admins?
    fun existsByUsername(username: String): Boolean
}