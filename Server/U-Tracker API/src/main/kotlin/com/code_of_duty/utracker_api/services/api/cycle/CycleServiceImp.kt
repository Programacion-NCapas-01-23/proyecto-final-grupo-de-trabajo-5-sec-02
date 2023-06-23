package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.FacultyDao
import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.utils.JwtUtils
import org.springframework.stereotype.Component
@Component
class CycleServiceImp(
    private val cycleDao: CycleDao,
    private val studentDao: StudentDao,
    private val facultyDao: FacultyDao,
    private val pensumDao: PensumDao,
    private val jwtUtils: JwtUtils
) : CycleService {
    override fun getAllCycles(): List<CycleDto> {
        //TODO: Check if the user is a student or an admin
        return emptyList()

    }
}