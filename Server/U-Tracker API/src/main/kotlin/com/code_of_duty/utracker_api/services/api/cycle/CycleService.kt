package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.StudentCycleCreatedDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleResponseDto
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
interface CycleService {
    fun getAllCycles(studentCode: String): List<StudentCycleResponseDto>
    fun createStudentCycle(studentCode: String, cycleType: Int, year: Int): StudentCycleCreatedDto
    fun addSubjectToStudentPerCycle(studentCycleId: UUID, subjectCode: String, estimateGrade: BigDecimal?)
    fun removeSubjectFromStudentPerCycle(studentCycleId: UUID, subjectCode: String)
    fun deleteStudentCycle(studentCode: String, studentCycleId: UUID)
    fun getStudentCycles(studentCode: String): List<StudentCycleDto>
    /*fun findBestStudentCycle(studentCode: String, userCycleId: String, subjects: List<String>): List<OrSchedulesDto>*/

}