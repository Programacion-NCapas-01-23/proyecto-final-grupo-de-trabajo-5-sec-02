package com.code_of_duty.utracker_api.services.admin.pensum

import com.code_of_duty.utracker_api.data.dtos.PensumDto
import org.springframework.stereotype.Service

@Service
interface AdminPensumService {
    fun addAllPensums(pensums: List<PensumDto>)

    fun deleteAllListedPensums(ids: List<String>)

    fun updatePensum(pensum: PensumDto)
}