package com.code_of_duty.u_tracker.data.repositories

import android.util.Log
import com.code_of_duty.u_tracker.data.database.dao.CycleDao
import com.code_of_duty.u_tracker.data.database.dao.TokenDao
import com.code_of_duty.u_tracker.data.database.dao.UserDao
import com.code_of_duty.u_tracker.data.database.entities.Cycle as CycleEntities
import com.code_of_duty.u_tracker.data.database.entities.UserToken
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import javax.inject.Inject
//TODO: Implement DataBase for this model
class PensumRepository @Inject constructor(
    private val apiClient: UtrackerApiClient,
    private val tokenDao: TokenDao,
    private val userDao: UserDao,
    private val cycleDao: CycleDao
): SafeApiRequest(){

    suspend fun getPensum(token: String): List<IdealTermResponse>{
        return apiRequest {
            Log.d("PensumRepository", "Getting pensum")
            apiClient.getPensum(token)
        }
    }

    suspend fun getToken(): String{
        val token = tokenDao.getToken()
        val user = userDao.getUser()
        if (token.isNotEmpty()){
            return try {
                Log.d("PensumRepository", "Testing token")
                testToken(token)
            } catch (_: Exception){
                Log.d("PensumRepository", "Token failed, trying to login")
                try {
                    Log.d("PensumRepository", "Trying to login")
                    tryLogin(token, user.code, user.password)
                } catch (_: Exception){
                    Log.d("PensumRepository", "Login failed")
                    throw Exception("No token found")
                }
            }
        }else{
            return try {
                Log.d("PensumRepository", "Trying to login, no token found")
                tryLogin(token, user.code, user.password)
            } catch (_: Exception){
                Log.d("PensumRepository", "Login failed, no token found")
                throw Exception("No token found")
            }
        }
    }

    private suspend fun testToken(token: String): String{
        apiRequest {
            apiClient.verifyToken(token)
        }
        return token
    }

    private suspend fun tryLogin(oldToken: String, code: String, password: String): String{
        val res = apiRequest {
            apiClient.login(
                LoginRequest(
                    code,
                    password
                )
            )
        }
        tokenDao.deleteToken(UserToken(oldToken))
        tokenDao.insertToken(UserToken(res.token))
        return res.token
    }

    suspend fun saveCycles(cycles: List<CycleEntities>) {
        cycleDao.insertCycle(cycles)
    }
}