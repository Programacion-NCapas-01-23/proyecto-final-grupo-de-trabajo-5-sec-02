package com.code_of_duty.u_tracker.data.network

import com.code_of_duty.u_tracker.data.network.request.AddSubjectToPersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.ChangePasswordRequest
import com.code_of_duty.u_tracker.data.network.request.CreatePersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.DeletePersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.DeleteSubjectFromPersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.request.SignUpRequest
import com.code_of_duty.u_tracker.data.network.response.DegreesResponse
import com.code_of_duty.u_tracker.data.network.response.FacultiesResponse
import com.code_of_duty.u_tracker.data.network.response.LoginResponse
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Query("email") email: String
    ): Response<MessageResponse>

    @PATCH("auth/changePassword")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<MessageResponse>

    @GET("cycle/")
    suspend fun getPensum(
        @Header("Authorization") token: String
    ): Response<List<IdealTermResponse>>

    @POST("/auth/verifyToken")
    suspend fun verifyToken(
        @Header("Authorization") token: String
    ): Response<MessageResponse>

    //Faculties
    @GET("faculty/")
    suspend fun getFaculties(): Response<List<FacultiesResponse>>

    @GET("degree/")
    suspend fun getDegreesByFaculty(
        @Query("id") facultyId: String
    ): Response<List<DegreesResponse>>

    @GET("cycle/getStudentCycles")
    suspend fun getPersonalTerms(
        @Header("Authorization") token: String
    ): Response<List<PersonalTermResponse>>

    @POST("cycle/createCycle")
    suspend fun  createPersonalTerm(
        @Header("Authorization") token: String,
        @Body createPersonalTermRequest: CreatePersonalTermRequest
    ): Response<MessageResponse>

    @POST("cycle/addSubject")
    suspend fun addSubjectToPersonalTerm(
        @Header("Authorization") token: String,
        @Body addSubjectToPersonalTermRequest: AddSubjectToPersonalTermRequest
    ): Response<MessageResponse>

    @DELETE("cycle/deleteCycle")
    suspend fun deletePersonalTerm(
        @Header("Authorization") token: String,
        @Body deletePersonalTermRequest: DeletePersonalTermRequest
    ): Response<MessageResponse>

    @DELETE("cycle/deleteSubject")
    suspend fun deleteSubjectFromPersonalTerm(
        @Header("Authorization") token: String,
        @Body deletePersonalTermRequest: DeleteSubjectFromPersonalTermRequest
    ): Response<MessageResponse>
}