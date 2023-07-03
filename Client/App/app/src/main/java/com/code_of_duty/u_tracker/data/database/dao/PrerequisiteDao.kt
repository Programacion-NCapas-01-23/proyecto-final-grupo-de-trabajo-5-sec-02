package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.code_of_duty.u_tracker.data.database.entities.Prerequisite

@Dao
interface PrerequisiteDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertPrerequisites(prerequisites: List<Prerequisite>)

    @Query("SELECT * FROM prerequisite WHERE subjectCode = :subjectCode")
    suspend fun getPrerequisitesBySubject(subjectCode: String): List<Prerequisite>
}