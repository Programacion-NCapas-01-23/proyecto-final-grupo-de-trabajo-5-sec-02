package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.database.dao.TokenDao
import com.code_of_duty.u_tracker.data.database.dao.UserDao
import com.code_of_duty.u_tracker.data.database.entities.User
import com.code_of_duty.u_tracker.data.database.entities.UserToken
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.response.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiClient: UtrackerApiClient,
    private val tokenDao: TokenDao,
    private val userDao: UserDao
): SafeApiRequest() {
    suspend fun login(code: String, password: String): LoginResponse{
        return apiRequest {
            apiClient.login(
                    LoginRequest(
                            code,
                            password
                    )
            )
        }
    }
    suspend fun saveToken(token: String) = tokenDao.insertToken(UserToken(token))
    suspend fun getToken() = tokenDao.getToken()
    suspend fun deleteToken(token: String) = tokenDao.deleteToken(UserToken(token))

    suspend fun saveUser(code: String, password: String) = userDao.insertUser(User(code, password))
}
