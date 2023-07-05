package com.code_of_duty.utracker_api.services.admin.schedule

import com.code_of_duty.utracker_api.data.dao.ClassTimeDao
import com.code_of_duty.utracker_api.data.dao.ScheduleDao
import com.code_of_duty.utracker_api.data.dao.SubjectDao
import com.code_of_duty.utracker_api.data.dtos.SchedulesDto
import com.code_of_duty.utracker_api.data.models.Schedule
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminScheduleServicesImpl(
    private val scheduleDao: ScheduleDao,
    private val subjectDao: SubjectDao,
    private val classTimeDao: ClassTimeDao
) : AdminScheduleService{

    override fun getAllSchedules(): List<SchedulesDto> {
        return scheduleDao.findAll().map {
            SchedulesDto(
                id = it.id.toString(),
                subject = it.subject.code,
                classTime = UUID.fromString(it.classTime.id.toString()),
                collection = it.collection
            )
        }
    }
    override fun addAllSchedules(schedules: List<SchedulesDto>) {
        schedules.forEach {
            val subject = subjectDao.findById(it.subject).orElseThrow {
                ExceptionNotFound("Subject with id ${it.subject} not found")
            }
            val classTime = classTimeDao.findById(it.classTime).orElseThrow {
                ExceptionNotFound("Class time with id ${it.classTime} not found")
            }

            scheduleDao.findBySubjectAndClassTime(subject, classTime)?.let {
                throw IllegalArgumentException(
                    "Schedule with subject ${subject.code} and class time ${classTime.id} already exists"
                )
            }

            scheduleDao.save(
                Schedule(
                    subject = subject,
                    classTime = classTime,
                    collection = it.collection
                )
            )
        }
    }

    override fun deleteAllListedSchedules(schedules: List<String>) = schedules.forEach {
        scheduleDao.deleteById(UUID.fromString(it))
    }

    override fun updateSchedule(schedule: SchedulesDto) {
        val scheduleToUpdate = scheduleDao.findById(UUID.fromString(schedule.id)).orElseThrow {
            ExceptionNotFound("Schedule with id ${schedule.id} not found")
        }

        val subject = subjectDao.findById(schedule.subject).orElseThrow {
            ExceptionNotFound("Subject with id ${schedule.subject} not found")
        }

        val classTime = classTimeDao.findById(schedule.classTime).orElseThrow {
            ExceptionNotFound("Class time with id ${schedule.classTime} not found")
        }

        val newSchedule = scheduleToUpdate.copy(
            subject = subject,
            classTime = classTime,
            collection = schedule.collection
        )
        scheduleDao.save(newSchedule)
    }

    override fun deleteAllSchedules() = scheduleDao.deleteAll()
}