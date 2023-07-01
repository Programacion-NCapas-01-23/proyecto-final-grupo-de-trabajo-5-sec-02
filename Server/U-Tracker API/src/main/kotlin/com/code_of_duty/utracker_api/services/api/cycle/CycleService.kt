package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleResponseDto
import org.springframework.stereotype.Service
import java.util.*

@Service
interface CycleService {
    fun getAllCycles(studentCode: String): List<StudentCycleResponseDto>
    fun createStudentCycle(studentCode: String, cycleType: Int, year: Int)
    fun addSubjectToStudentPerCycle(studentCycleId: UUID, subjectCode: String)
    fun removeSubjectFromStudentPerCycle(studentCycleId: UUID, subjectCode: String)
    fun deleteStudentCycle(studentCode: String, studentCycleId: UUID)
    fun getStudentCycles(studentCode: String): List<StudentCycleDto>
    fun findBestStudentCycle(studentCode: String, userCycleId: String, subjects: List<String>): StudentCycleDto

}