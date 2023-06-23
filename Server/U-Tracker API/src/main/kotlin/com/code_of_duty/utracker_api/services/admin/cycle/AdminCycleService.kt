package com.code_of_duty.utracker_api.services.admin.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.models.Cycle
import org.springframework.stereotype.Service

@Service
interface AdminCycleService {

    fun addAllCycles(cycles: List<CycleDto>)
    fun deleteAllCycles(cycles: List<String>)
    fun updateCycle(cycle: CycleDto)

    fun getAllCycles(): List<Cycle>
}