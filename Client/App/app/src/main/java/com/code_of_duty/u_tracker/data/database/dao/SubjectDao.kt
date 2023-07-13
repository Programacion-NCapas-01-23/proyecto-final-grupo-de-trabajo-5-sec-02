package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.code_of_duty.u_tracker.data.database.entities.Subject

@Dao
interface SubjectDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)
    @Query("SELECT * FROM subject WHERE cycle = :cycle")
    suspend fun getSubjectsByCycle(cycle: Int): List<Subject>
    @Query("SELECT * FROM subject WHERE code = :code")
    suspend fun getSubjectByCode(code: String): Subject
    @Query("SELECT subject.* FROM subject LEFT JOIN grade ON subject.code = grade.code")
    suspend fun getSubjectWithoutGrade(): List<Subject>
    @Update
    suspend fun updateSubject(subject: Subject)
    @Insert
    suspend fun insertSubject(subject: Subject)
    @Query("SELECT * FROM subject")
    suspend fun getAllSubject(): List<Subject>
}