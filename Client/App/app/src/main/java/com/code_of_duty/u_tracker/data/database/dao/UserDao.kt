package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.code_of_duty.u_tracker.data.database.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getUser(): User

    @Delete
    suspend fun deleteUser(user: User)
}