package com.code_of_duty.utracker_api.services.api.degree

import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.stereotype.Service
import java.util.*

@Service
interface DegreeService {

    fun getDegreebyId(id: UUID): Degree?
}