package com.code_of_duty.u_tracker.data.repositories

import android.util.Log
import com.code_of_duty.u_tracker.data.database.dao.PersonalTermDao
import com.code_of_duty.u_tracker.data.database.dao.PersonalTermXSubjectDao
import com.code_of_duty.u_tracker.data.database.dao.TokenDao
import com.code_of_duty.u_tracker.data.database.dao.UserDao
import com.code_of_duty.u_tracker.data.database.entities.PersonalTerm
import com.code_of_duty.u_tracker.data.database.entities.PersonalTermSubjectCrossRef
import com.code_of_duty.u_tracker.data.database.entities.UserToken
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.database.entities.PersonalTerm as PersonalTermEntities
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.AddSubjectToPersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.CreatePersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.DeletePersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.DeleteSubjectFromPersonalTermRequest
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.response.MessageResponse
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import javax.inject.Inject

class TermRepository @Inject constructor(
    private val apiClient: UtrackerApiClient,
    private val tokenDao: TokenDao,
    private val userDao: UserDao,
    private val personalTermDao: PersonalTermDao,
    private val subjectForTermDao: PersonalTermXSubjectDao
    ): SafeApiRequest() {

    suspend fun getPersonalTerms(token:String): List<PersonalTermResponse> {
        return apiRequest {
            Log.d("TermRepository", "Getting personal terms")
            apiClient.getPersonalTerms(token)
        }
    }

    suspend fun addPersonalTerm(token:String, cycleType: Int, year: Int): MessageResponse {
        return apiRequest {
            apiClient.createPersonalTerm(token, CreatePersonalTermRequest(cycleType, year))
        }
    }

    suspend fun deletePersonalTerm(token: String, personalTermId: String): MessageResponse {
        return apiRequest {
            apiClient.deletePersonalTerm(token, DeletePersonalTermRequest(personalTermId))
        }
    }

    suspend fun addSubjectToPersonalTerm(token: String, studentTermId: String, subjectCode: String, estimateGrade: Int): MessageResponse {
        return apiRequest {
            apiClient.addSubjectToPersonalTerm(token, AddSubjectToPersonalTermRequest(studentTermId, subjectCode, estimateGrade))
        }
    }

    suspend fun deleteSubjectFromPersonalTerm(token: String, studentTermId: String, subjectCode: String): MessageResponse {
        return apiRequest {
            apiClient.deleteSubjectFromPersonalTerm(token, DeleteSubjectFromPersonalTermRequest(studentTermId, subjectCode))
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

    suspend fun savePersonalTerms(personalTerms: List<PersonalTermEntities>) {
        personalTermDao.insertPersonalTerms(personalTerms)
    }

    suspend fun saveSubjectsForTerm(subjectsForTerm: List<PersonalTermSubjectCrossRef>) {
        subjectForTermDao.insertSubjectsIntoTerm(subjectsForTerm)
    }

    suspend fun getPersonalTerms(): List<PersonalTermEntities> {
        return personalTermDao.getPersonalTerms()
    }

    suspend fun getSubjectsFromTerm(termId: String): List<PersonalTermSubjectCrossRef> {
        return subjectForTermDao.getSubjectsFromPersonalTerm(termId)
    }

    suspend fun updatePersonalTerm(personalTerm: PersonalTermEntities) {
        personalTermDao.updatePersonalTerm(personalTerm)
    }

    suspend fun updateSubjectForTerm(subjectForTerm: PersonalTermSubjectCrossRef) {
        subjectForTermDao.updateSubjectsForPersonalTerm(subjectForTerm)
    }

    suspend fun deletePersonalTerm(personalTermId: String) {
        personalTermDao.deletePersonalTerm(personalTermId)
    }

    suspend fun deleteSubjectForTerm(subjectCode: String, termId: String) {
        subjectForTermDao.deleteSubjectFromPersonalTerm(subjectCode, termId)
    }

    suspend fun insertPersonalTerm(personalTerm: PersonalTermResponse, subjectsForTerm: List<String>) {
        personalTermDao.insertOnePersonalTerm(PersonalTerm(
            id = personalTerm.id,
            studentCode = personalTerm.studentCode,
            cycleType = personalTerm.cycleType,
            year = personalTerm.year,
            subjects = subjectsForTerm
        ))
    }

    suspend fun insertSubjectForTerm(subjectCode: String, termId: String) {
        subjectForTermDao.insertOneSubjectForPersonalTerm(PersonalTermSubjectCrossRef(
            subjectCode = subjectCode,
            personalTermId = termId
        ))
    }
}
