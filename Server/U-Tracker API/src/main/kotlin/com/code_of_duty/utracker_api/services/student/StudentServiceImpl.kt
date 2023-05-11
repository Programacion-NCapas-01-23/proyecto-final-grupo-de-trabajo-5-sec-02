package com.code_of_duty.utracker_api.services.student

import com.code_of_duty.utracker_api.data.dao.StudentDao
import org.springframework.stereotype.Component

@Component
class StudentServiceImpl(private val studentDao: StudentDao): StudentService {

    override fun getStudent(code: String) = studentDao.findByCode(code)
}