package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.code_of_duty.u_tracker.data.database.entities.Grade

@Dao
interface GradeDao {

    @Insert
    suspend fun insert(grade: Grade)
    @Update
    suspend fun update(grade: Grade)
    @Query("SELECT * FROM grade WHERE passed = 1")
    suspend fun getCompleted(): List<Grade>
    @Query("SELECT * FROM grade WHERE code = :code")
    suspend fun getGradeBySubject(code: String): Grade
}