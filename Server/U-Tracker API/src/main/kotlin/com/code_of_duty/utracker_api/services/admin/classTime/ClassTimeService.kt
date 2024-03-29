package com.code_of_duty.utracker_api.services.admin.classTime

import com.code_of_duty.utracker_api.data.dtos.ClassTimeDto
import com.code_of_duty.utracker_api.data.dtos.ClassTimeUpdateDto
import com.code_of_duty.utracker_api.data.models.ClassTime
import org.springframework.stereotype.Service
import java.util.*

@Service
interface ClassTimeService {

    fun getAllClassTimes(): List<ClassTime>
    fun addClassTime(classesTimes: List<ClassTimeDto>)
    fun deleteClassTime(classesTimes: List<UUID>)
    fun updateClassTime(classTime: ClassTimeUpdateDto)
}