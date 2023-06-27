package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.ClassTime
import com.code_of_duty.utracker_api.data.models.Schedule
import com.code_of_duty.utracker_api.data.models.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.scheduling.support.ScheduledMethodRunnable
import java.util.UUID

interface ScheduleDao : JpaRepository<Schedule, UUID> {
    fun findBySubjectAndClassTime(subject: Subject?, classTime: ClassTime?): Schedule?

    fun findBySubjectCodeIn(subjectId: List<String>): List<Schedule>

}