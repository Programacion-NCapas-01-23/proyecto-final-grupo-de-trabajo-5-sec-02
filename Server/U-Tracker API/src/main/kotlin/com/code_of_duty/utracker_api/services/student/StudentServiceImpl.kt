package com.code_of_duty.utracker_api.services.student

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.ChangePasswordDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.utils.PasswordUtils
import com.code_of_duty.utracker_api.utils.StudentCodeNotMatchException
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StudentServiceImpl(private val studentDao: StudentDao): StudentService {

    @Autowired
    lateinit var passwordUtils: PasswordUtils
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

    override fun changePassword(code: String, changePasswordDto: ChangePasswordDto) {
        val user = studentDao.findById(code).orElse(null)
            ?: throw StudentNotFoundException("Student with code $code not found")

        val verifyPassword = passwordUtils.verifyPassword(changePasswordDto.oldPassword, user.hashPassword)

        if(!verifyPassword) throw IllegalArgumentException("Old password is incorrect")

        if (changePasswordDto.newPassword != changePasswordDto.confirmPassword)
            throw IllegalArgumentException("Passwords do not match")

        val hashPassword = passwordUtils.hashPassword(changePasswordDto.newPassword)

        val student = user.copy(hashPassword = hashPassword)

        studentDao.save(student)
    }
}