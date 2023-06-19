package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dtos.CycleDto
import org.springframework.stereotype.Service

@Service
interface CycleService {
    fun getAllCycles(): List<CycleDto>
}