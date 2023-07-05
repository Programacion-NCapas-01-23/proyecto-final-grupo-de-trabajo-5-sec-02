package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.database.dao.GradeDao
import com.code_of_duty.u_tracker.data.database.dao.SubjectDao
import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import javax.inject.Inject

class CumRepository @Inject constructor(
    private val subjectDao: SubjectDao,
    private val gradeDao: GradeDao
) : SafeApiRequest() {
    suspend fun getTotal() = subjectDao.getAllSubject()

    suspend fun getCompleted() = gradeDao.getCompleted()

}
