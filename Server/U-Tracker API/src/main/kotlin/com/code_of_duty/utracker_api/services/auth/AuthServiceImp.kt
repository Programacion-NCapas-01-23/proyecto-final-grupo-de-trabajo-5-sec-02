package com.code_of_duty.utracker_api.services.auth

import com.code_of_duty.utracker_api.data.dao.LoginDao
import com.code_of_duty.utracker_api.data.models.Student
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import org.springframework.stereotype.Repository

@Repository
class AuthServiceImp(private val entityManager: EntityManager) : LoginDao {
    override fun findByCode(code: String?): Student? {
        val query = entityManager.createQuery(
            "SELECT s FROM Student s WHERE s.code = :code",
            Student::class.java
        )
        query.setParameter("code", code)
        return try {
            query.singleResult
        } catch (e: NoResultException) {
            null
        }
    }
}