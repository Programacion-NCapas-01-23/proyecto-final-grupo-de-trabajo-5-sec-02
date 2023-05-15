package com.code_of_duty.utracker_api.services.student

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.utils.StudentCodeNotMatchException
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import org.springframework.stereotype.Component

@Component
class StudentServiceImpl(private val studentDao: StudentDao): StudentService {

    override fun getStudent(code: String): Student? = studentDao.findById(code).orElse(null)

    override fun updateStudent(code: String, student: StudentRequestDto) {
        val currentStudent = studentDao.findById(code).orElse(null)
            ?: throw StudentNotFoundException("Student with code $code not found")

        if (currentStudent.code != student.code) throw StudentCodeNotMatchException("Student code does not match")

        currentStudent.let {
            it.username = student.username
            it.image = student.image
            studentDao.save(it)
        }
    }
}