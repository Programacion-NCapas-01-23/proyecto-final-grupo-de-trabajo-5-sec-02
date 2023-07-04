package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.code_of_duty.u_tracker.data.database.entities.Grade

@Dao
interface GradeDao {

    @Insert
    fun insertOrUpdate(grade: Grade)
}