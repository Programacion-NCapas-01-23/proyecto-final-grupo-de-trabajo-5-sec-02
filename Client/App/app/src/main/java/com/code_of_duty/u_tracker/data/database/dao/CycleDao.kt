package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.code_of_duty.u_tracker.data.database.entities.Cycle

@Dao
interface CycleDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCycle(cycles: List<Cycle>)
}