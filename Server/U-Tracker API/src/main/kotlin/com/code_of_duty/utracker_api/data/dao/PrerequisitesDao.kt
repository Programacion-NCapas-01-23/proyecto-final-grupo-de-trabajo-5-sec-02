package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Prerequisite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface PrerequisitesDao : JpaRepository<Prerequisite, Long> {
    @Query("SELECT p FROM Prerequisite p WHERE p.prerequisite.subjectCode.code IN :subjects")
    fun getPrerequisitesForSubjects(subjects: List<String>): List<Prerequisite>

    @Query("SELECT p FROM Prerequisite p WHERE p.prerequisite.subjectCode.code = :subjectCode")
    fun findBySubjectCode(subjectCode: String): List<Prerequisite>

    @Query("SELECT p FROM Prerequisite p WHERE p.prerequisite.subjectCode.code = :subjectCode AND p.prerequisite.prerequisiteCode.correlative = :prerequisiteCorrelative")
    fun findBySubjectCodeAndPrerequisiteCorrelative(subjectCode: String, prerequisiteCorrelative: Int): Prerequisite?

    fun findByPrerequisiteSubjectCode(subjectCode: String): List<Prerequisite>
}
