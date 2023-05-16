package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.VerificationToken
import org.springframework.data.repository.ListCrudRepository

interface VerificationTokenDao: ListCrudRepository<VerificationToken, Long> {
    fun findByToken(token: String): VerificationToken?
    fun existsByToken(token: String): Boolean
}