package com.code_of_duty.utracker_api.services.admin.classTime

import com.code_of_duty.utracker_api.data.dao.ClassTimeDao
import com.code_of_duty.utracker_api.data.dtos.ClassTimeDto
import com.code_of_duty.utracker_api.data.dtos.ClassTimeUpdateDto
import com.code_of_duty.utracker_api.data.enums.Days
import com.code_of_duty.utracker_api.data.models.ClassTime
import com.code_of_duty.utracker_api.utils.GeneralUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.util.*

@Component
class ClassTimeServiceImpl : ClassTimeService{
    @Autowired
    lateinit var classTimeDao: ClassTimeDao
    @Autowired
    lateinit var generalUtils: GeneralUtils

    override fun getAllClassTimes(): List<ClassTime> = classTimeDao.findAll()
    override fun addClassTime(classesTimes: List<ClassTimeDto>) {
        classesTimes.forEach {
            val day = Days.fromInt(it.day)
            val time = LocalTime.parse(it.start)

            if(classTimeDao.existsByDayAndStartHourAndTotalHours(day, time, it.end))
                throw IllegalArgumentException("Class time ${classesTimes.indexOf(it)} already exists")

            val classTime = ClassTime(
                day = day,
                startHour = time,
                totalHours = it.end
            )
            classTimeDao.save(classTime)
        }
    }

    override fun deleteClassTime(classesTimes: List<UUID>) = classTimeDao.deleteByIdIn(classesTimes)

    override fun updateClassTime(classTime: ClassTimeUpdateDto){
        val day = Days.fromInt(classTime.day)

        val time = LocalTime.parse(classTime.startHour)

        val classTimeToUpdate = classTimeDao.findById(UUID.fromString(classTime.id)).orElseThrow{
            throw IllegalArgumentException("Class time not found")
        }

        val updateClassTime = ClassTime(
            id = classTimeToUpdate.id,
            day = day,
            startHour = time,
            totalHours = classTime.totalHours

        )
        classTimeDao.save(updateClassTime)
    }

}