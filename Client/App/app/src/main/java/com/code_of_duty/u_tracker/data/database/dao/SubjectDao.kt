package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.code_of_duty.u_tracker.data.database.entities.Subject

@Dao
interface SubjectDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)

    @Query("SELECT * FROM subject WHERE cycle = :cycle")
    suspend fun getSubjectsByCycle(cycle: Int): List<Subject>
}