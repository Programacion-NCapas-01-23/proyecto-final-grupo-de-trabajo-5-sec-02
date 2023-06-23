package com.code_of_duty.u_tracker.data.network

import com.code_of_duty.u_tracker.data.network.request.ChangePasswordRequest
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.request.RegisterRequest
import com.code_of_duty.u_tracker.data.network.response.LoginResponse
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UtrackerApiClient {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): MessageResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("auth/getVerificationToken")
    suspend fun getVerificationToken(
        @Path("email") email: String
    ): MessageResponse

    @POST("auth/changePassword")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): MessageResponse
}