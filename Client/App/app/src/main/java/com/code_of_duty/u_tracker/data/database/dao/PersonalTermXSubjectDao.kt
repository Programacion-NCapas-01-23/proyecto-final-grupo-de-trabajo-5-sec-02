package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.code_of_duty.u_tracker.data.database.entities.PersonalTermSubjectCrossRef

interface PersonalTermXSubjectDao {

    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertSubjectsIntoTerm(subjectsForTerm: List<PersonalTermSubjectCrossRef>)

    @Query("SELECT * FROM subject_x_term WHERE personalTermId = :personalTermId")
    suspend fun getSubjectsFromPersonalTerm(personalTermId: String): List<PersonalTermSubjectCrossRef>

    @Query("SELECT * FROM subject_x_term WHERE subjectCode = :subjectCode")
    suspend fun getPersonalTermsFromSubject(subjectCode: String): List<PersonalTermSubjectCrossRef>

    @Update
    suspend fun updateSubjectsForPersonalTerm (existingSubjectsForPersonalTerm: PersonalTermSubjectCrossRef)

    @Insert
    suspend fun insertOneSubjectForPersonalTerm(subjectForPersonalTerm: PersonalTermSubjectCrossRef)

    @Query("DELETE FROM subject_x_term WHERE subjectCode = :subjectCode AND personalTermId = :personalTermId")
    suspend fun deleteSubjectFromPersonalTerm(subjectCode: String, personalTermId: String)
}