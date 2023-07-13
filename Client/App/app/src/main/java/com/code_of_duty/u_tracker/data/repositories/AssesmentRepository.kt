package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.database.dao.SubjectDao
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import javax.inject.Inject

class AssesmentRepository @Inject constructor(
    private val apiClient: UtrackerApiClient,
    private val subjectDao: SubjectDao
): SafeApiRequest() {
    suspend fun getSubjectWithoutGrade() = subjectDao.getAllSubject()
}