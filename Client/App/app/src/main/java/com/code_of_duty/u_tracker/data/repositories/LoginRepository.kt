package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.response.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiClient: UtrackerApiClient
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

}
