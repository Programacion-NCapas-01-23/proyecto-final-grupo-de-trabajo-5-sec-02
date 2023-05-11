package com.code_of_duty.utracker_api.services.login

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Service

@Service
class LoginService(private val studentDao: StudentDao) {

    fun getStudentByCode(code: String): Student? {
        return studentDao.findByCode(code)
    }
}
