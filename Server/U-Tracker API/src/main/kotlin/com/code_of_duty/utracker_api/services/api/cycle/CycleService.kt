package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import org.springframework.stereotype.Service
import java.util.UUID

@Service
interface CycleService {
    fun getAllCycles(studentCode: String): List<CycleDto>
    fun createStudentCycle(studentCode: String, cycleType: Int, year: Int)
    fun getStudentCycle(studentCode: String, studentCycleId: UUID): StudentCycleDto
    fun addSubjectToStudentCycle(studentCode: String, studentCycleId: UUID, subjectCode: String)
    fun removeSubjectFromStudentCycle(studentCode: String, studentCycleId: UUID, subjectCode: String)
    fun deleteStudentCycle(studentCode: String, studentCycleId: UUID)
    fun findBestStudentCycle(studentCode: String, userCycleId: String, subjects: List<String>): StudentCycleDto

}