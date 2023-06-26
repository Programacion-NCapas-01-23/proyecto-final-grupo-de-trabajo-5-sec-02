package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiClient: UtrackerApiClient
) {
    suspend fun login(code: String, password: String): Boolean{
        val request = LoginRequest(code, password)
        val response = apiClient.login(request)
        return true
    }

}
