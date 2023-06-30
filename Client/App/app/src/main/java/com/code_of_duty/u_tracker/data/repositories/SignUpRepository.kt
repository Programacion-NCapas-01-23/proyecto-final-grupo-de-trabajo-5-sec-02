package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.request.SignUpRequest
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val apiClient: UtrackerApiClient): SafeApiRequest() {
        suspend fun signup(code: String, username: String, email: String, password: String, degreeId: String): MessageResponse {
            return apiRequest {
                apiClient.signup(
                    SignUpRequest(
                        code,
                        username,
                        email,
                        password,
                        degreeId
                    )
                )
            }
        }
    }
