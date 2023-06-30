package com.code_of_duty.u_tracker.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.code_of_duty.u_tracker.data.database.entities.UserToken

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertToken(token: UserToken)

    @Query("SELECT * FROM userToken")
    suspend fun getToken(): String
    //delete all tokens using the @Delete annotation and the deleteToken() method
    @Delete
    suspend fun deleteToken(token: UserToken)
}