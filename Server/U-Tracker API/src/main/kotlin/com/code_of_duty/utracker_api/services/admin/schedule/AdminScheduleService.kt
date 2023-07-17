package com.code_of_duty.utracker_api.services.admin.schedule

import com.code_of_duty.utracker_api.data.dtos.SchedulesDto
import org.springframework.stereotype.Service

@Service
interface AdminScheduleService {
    fun getAllSchedules(): List<SchedulesDto>
    fun addAllSchedules(schedules: List<SchedulesDto>)
    fun deleteAllListedSchedules(schedules: List<String>)
    fun updateSchedule(schedule: SchedulesDto)
    fun deleteAllSchedules()
}