package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import org.springframework.stereotype.Service
import java.util.UUID

@Service
interface CycleService {
    fun getAllCycles(userDegree: UUID): List<CycleDto>
    fun createStudentCycle(studentUUID: UUID, userCycleId: String, subjects: List<UUID>): StudentCycleDto
}