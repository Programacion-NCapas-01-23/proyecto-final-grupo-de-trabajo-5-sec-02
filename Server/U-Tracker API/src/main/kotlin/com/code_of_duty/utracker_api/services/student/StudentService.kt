package com.code_of_duty.utracker_api.services.student

import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Service

@Service
interface StudentService {
    fun getStudent(code: String): Student?
}