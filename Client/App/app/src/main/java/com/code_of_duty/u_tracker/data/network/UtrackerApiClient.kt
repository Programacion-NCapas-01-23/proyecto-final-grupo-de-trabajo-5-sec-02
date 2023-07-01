package com.code_of_duty.u_tracker.data.network

import com.code_of_duty.u_tracker.data.network.request.ChangePasswordRequest
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.request.SignUpRequest
import com.code_of_duty.u_tracker.data.network.response.LoginResponse
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import com.code_of_duty.u_tracker.ui.models.Cycle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UtrackerApiClient {

    @POST("auth/register")
    suspend fun signup(
        @Body signUpRequest: SignUpRequest
    ): Response<MessageResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("auth/getVerificationToken")
    suspend fun getVerificationToken(
        @Path("email") email: String
    ): Response<MessageResponse>

    @POST("auth/changePassword")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<MessageResponse>

    @GET("cycle/")
    suspend fun getPensum(
        @Header("Authorization") token: String
    ): Response<List<Cycle>>

    @POST("/auth/verifyToken")
    suspend fun verifyToken(
        @Header("Authorization") token: String
    ): Response<MessageResponse>
}