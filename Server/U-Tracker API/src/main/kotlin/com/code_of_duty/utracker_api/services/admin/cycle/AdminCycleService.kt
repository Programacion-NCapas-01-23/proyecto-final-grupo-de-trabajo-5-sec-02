package com.code_of_duty.utracker_api.services.admin.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import org.springframework.stereotype.Service

@Service
interface AdminCycleService {
    fun addAllCycles(cycles: List<CycleDto>)
    fun deleteAllCycles(cycles: List<String>)
    fun updateCycle(cycle: CycleDto)
}