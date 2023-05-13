package com.code_of_duty.utracker_api.services.student

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Component

@Component
class StudentServiceImpl(private val studentDao: StudentDao): StudentService {

    override fun getStudent(code: String): Student? = studentDao.findById(code).orElse(null)
}