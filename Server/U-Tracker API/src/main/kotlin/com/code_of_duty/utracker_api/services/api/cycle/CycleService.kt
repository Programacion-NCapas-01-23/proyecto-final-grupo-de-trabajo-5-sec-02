package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.data.models.Cycle
import org.springframework.stereotype.Service
import java.util.*

@Service
interface CycleService {
    //TODO(cambiar de cycles a CycleDTO)
    fun getAllCycles(studentCode: String): List<CycleDto>
    fun createStudentCycle(studentUUID: UUID, userCycleId: String, subjects: List<UUID>): StudentCycleDto


}