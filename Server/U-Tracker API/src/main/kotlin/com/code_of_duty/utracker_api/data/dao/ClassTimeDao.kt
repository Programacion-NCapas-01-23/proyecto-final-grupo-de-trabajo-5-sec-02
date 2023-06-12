package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.enums.Days
import com.code_of_duty.utracker_api.data.models.ClassTime
import jakarta.transaction.Transactional
import org.springframework.data.repository.ListCrudRepository
import java.time.LocalTime
import java.util.UUID

interface ClassTimeDao: ListCrudRepository<ClassTime, UUID> {
    fun existsByDayAndStartHourAndTotalHours(day: Days, startHours:LocalTime, totalHours: Int): Boolean
    @Transactional(rollbackOn = [Exception::class])
    fun deleteByIdIn(ids: List<UUID>)
}