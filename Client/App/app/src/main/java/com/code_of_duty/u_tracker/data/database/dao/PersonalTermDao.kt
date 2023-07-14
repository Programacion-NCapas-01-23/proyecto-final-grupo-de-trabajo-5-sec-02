package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.code_of_duty.u_tracker.data.database.entities.PersonalTerm

interface PersonalTermDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertPersonalTerms(personalTerm: List<PersonalTerm>)

    @Query("SELECT * FROM personal_term_table")
    suspend fun getPersonalTerms(): List<PersonalTerm>

    @Update
    suspend fun updatePersonalTerm(existingPersonalTerm: PersonalTerm)

    @Insert
    suspend fun insertOnePersonalTerm(personalTerm: PersonalTerm)

    @Query("DELETE FROM personal_term_table WHERE id = :id")
    suspend fun deletePersonalTerm(id: String)
}