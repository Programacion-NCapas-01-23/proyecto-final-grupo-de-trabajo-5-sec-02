package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.code_of_duty.u_tracker.data.database.entities.Cycle
import com.code_of_duty.u_tracker.data.database.entities.MainTerm

@Dao
interface CycleDao {
    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCycle(cycles: List<Cycle>)

    @Query("SELECT * FROM cycle_table")
    suspend fun getCycles(): List<Cycle>

    @Update
    suspend fun updateCycle(existingCycle: Cycle)
    @Insert
    suspend fun insertOneCycle(cycle: Cycle)

    @Insert
    suspend fun insertMainTerm(cycle: MainTerm)

    @Query("SELECT * FROM MainTerm")
    suspend fun getMainTerm(): MainTerm
}