package com.code_of_duty.utracker_api.services.api.student

import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.ChangePasswordDto
import com.code_of_duty.utracker_api.data.dtos.StudentDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.utils.PasswordUtils
import com.code_of_duty.utracker_api.utils.StudentCodeNotMatchException
import com.code_of_duty.utracker_api.utils.StudentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StudentServiceImpl(
    private val studentDao: StudentDao,
    private val pensumDao: PensumDao
): StudentService {

    @Autowired
    lateinit var passwordUtils: PasswordUtils
    override fun getStudent(code: String): StudentDto? {
        val student = studentDao.findByCode(code)
        val pensum = pensumDao.findByStudentCode(code)
        if (student != null) {
            val approvedSubjectsCount = studentDao.countApprovedSubjectsByStudentCode(code)
            return StudentDto(
                code = student.code,
                name = student.username,
                email = student.email,
                image = student.image ?: "",
                cum = student.cum,
                degree = student.degree?.name ?: "",
                faculty = student.faculty?.name ?: "",
                pensum = pensum!!.plan,
                approvedSubjects = approvedSubjectsCount
            )
        }
        return null
    }


    override fun updateStudent(code: String, student: StudentRequestDto) {
        val currentStudent = studentDao.findById(code).orElse(null)
            ?: throw StudentNotFoundException("Student with code $code not found")

        if (currentStudent.code != student.code) throw StudentCodeNotMatchException("Student code does not match")

        val newStudent = currentStudent.copy(
            username = student.username,
            image = student.image
        )

        studentDao.save(newStudent)
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

    override fun findByEmail(email: String) = studentDao.findByEmail(email)
}