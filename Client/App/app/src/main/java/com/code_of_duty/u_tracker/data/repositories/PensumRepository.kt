package com.code_of_duty.u_tracker.data.repositories

import android.util.Log
import com.code_of_duty.u_tracker.data.database.dao.CycleDao
import com.code_of_duty.u_tracker.data.database.dao.GradeDao
import com.code_of_duty.u_tracker.data.database.dao.PrerequisiteDao
import com.code_of_duty.u_tracker.data.database.dao.SubjectDao
import com.code_of_duty.u_tracker.data.database.dao.TokenDao
import com.code_of_duty.u_tracker.data.database.dao.UserDao
import com.code_of_duty.u_tracker.data.database.entities.Grade
import com.code_of_duty.u_tracker.data.database.entities.Prerequisite
import com.code_of_duty.u_tracker.data.database.entities.Subject
import com.code_of_duty.u_tracker.data.database.entities.Cycle as CycleEntities
import com.code_of_duty.u_tracker.data.database.entities.UserToken
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.request.LoginRequest
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import com.code_of_duty.u_tracker.data.network.response.SubjectsFromTermResponse
import javax.inject.Inject
//TODO: Implement DataBase for this model
class PensumRepository @Inject constructor(
    private val apiClient: UtrackerApiClient,
    private val tokenDao: TokenDao,
    private val userDao: UserDao,
    private val cycleDao: CycleDao,
    private val subjectDao: SubjectDao,
    private val prerequisiteDao: PrerequisiteDao,
    private val gradeDao: GradeDao
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

    suspend fun saveSubjects(subjects: List<Subject>) {
        subjectDao.insertSubjects(subjects)
    }

    suspend fun getCycles(): List<CycleEntities> {
        return cycleDao.getCycles()
    }

    suspend fun getSubjects(cycle: Int): List<Subject> {
        return subjectDao.getSubjectsByCycle(cycle)
    }

    suspend fun savePrerequisites(prerequisites: List<Prerequisite>) {
        prerequisiteDao.insertPrerequisites(prerequisites)
    }

    suspend fun getPrerequisites(subject: String): List<Prerequisite> {
        return prerequisiteDao.getPrerequisitesBySubject(subject)
    }

    suspend fun updateSubjectgrade(subject: String, grade: Float) {
        val grade = Grade(subject, grade, grade >= 6.0)
        gradeDao.insertOrUpdate(grade)
    }

    suspend fun updateCycle(existingCycle: CycleEntities) {
        cycleDao.updateCycle(existingCycle)

    }

    suspend fun insertCycle(newCycle: IdealTermResponse) {
        cycleDao.insertOneCycle(CycleEntities(
            id = newCycle.orderValue,
            name = newCycle.name,
            cycleType = newCycle.cycleType,
            orderValue = newCycle.orderValue
        ))
    }

    suspend fun getSubjectByCode(code: String): Subject {
        return subjectDao.getSubjectByCode(code)
    }

    suspend fun updateSubject(subject: Subject) {
        subjectDao.updateSubject(subject)
    }

    suspend fun insertSubject(subject: SubjectsFromTermResponse, cycle: Int) {
        subjectDao.insertSubject(Subject(
            code = subject.code,
            name = subject.name,
            order = subject.correlative,
            uv = subject.uv,
            cycle = cycle,
        ))
    }

    suspend fun deletePrerequisite(code: String) {
        prerequisiteDao.deletePrerequisite(code)
    }

    suspend fun getGrade(code: String): Grade {
        return gradeDao.getGradeBySubject(code)
    }
}