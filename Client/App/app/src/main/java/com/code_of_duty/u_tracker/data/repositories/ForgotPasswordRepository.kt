package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.ChangePasswordRequest
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(
    private val api: UtrackerApiClient
): SafeApiRequest() {
    suspend fun generateToken(email: String): MessageResponse {
        return apiRequest {
            api.getVerificationToken(email)
        }
    }

    suspend fun changePassword(email: String, token: String, password: String, confirmPassword: String): MessageResponse {
        return apiRequest {
            api.changePassword(
                ChangePasswordRequest(
                    email,
                    token,
                    password,
                    confirmPassword
                )
            )
        }

    }
}
