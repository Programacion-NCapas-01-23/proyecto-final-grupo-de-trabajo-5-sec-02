package com.code_of_duty.utracker_api.services.admin.subject

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.SubjectDao
import com.code_of_duty.utracker_api.data.dao.SubjectPerCycleDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.CycleRelationDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerCycle
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AdminSubjectServiceImpl(
    private val subjectDao: SubjectDao,
    private val cycleDao: CycleDao,
    private val subjectPerCycleDao: SubjectPerCycleDao
) : AdminSubjectService {

    override fun getAllSubjects(
        nameFilter: String?,
        sortBy: String?,
        degreeFilter: String?,
        pensumFilter: String?,
        facultyFilter: String?
    ): List<SubjectDto> {
        var subjects = subjectDao.findAllWithSubjectPerCycles()

        if (nameFilter != null) {
            subjects = subjects.filter { it.name.startsWith(nameFilter, ignoreCase = true) }
        }

        if (degreeFilter != null || pensumFilter != null || facultyFilter != null) {
            subjects = subjects.filter { subject ->
                subject.subjectPerCycles.all { subjectPerCycle ->
                    val degreeMatches = degreeFilter == null || subjectPerCycle.cycle.pensum?.degree?.name.equals(degreeFilter, ignoreCase = true)
                    val pensumMatches = pensumFilter == null || subjectPerCycle.cycle.pensum?.plan.equals(pensumFilter, ignoreCase = true)
                    val facultyMatches = facultyFilter == null || subjectPerCycle.cycle.pensum?.degree?.faculty?.name.equals(facultyFilter, ignoreCase = true)
                    degreeMatches && pensumMatches && facultyMatches
                }
            }
        }

        return subjects.map { subject ->
            val cycleRelation = subject.subjectPerCycles.map { subjectPerCycle ->
                val cycle = cycleDao.findById(subjectPerCycle.cycle.id)
                    .orElseThrow { EntityNotFoundException("Cycle not found with id: ${subjectPerCycle.cycle.id}") }

                CycleRelationDto(
                    id = cycle.id.toString(),
                    correlative = subjectPerCycle.correlative,
                    cycle = CycleDto(
                        id = cycle.id.toString(),
                        type = cycle.cycleType.ordinal,
                        name = cycle.name,
                        pensumId = cycle.pensum?.id.toString()
                    )
                )
            }

            SubjectDto(
                code = subject.code,
                name = subject.name,
                uv = subject.uv,
                estimateGrade = subject.estimateGrade,
                cycleRelation = cycleRelation
            )
        }
    }
    override fun addAllSubjects(subjects: List<SubjectDto>) {
        subjects.forEach {
            val newSubject = Subject(
                code = it.code,
                name = it.name,
                uv = it.uv
            )
            if (it.estimateGrade != null) {
                newSubject.estimateGrade = it.estimateGrade
            }
            subjectDao.save(newSubject)
            it.cycleRelation?.forEach {cycleRelationDto ->
                val cycle = cycleDao.findById(UUID.fromString(cycleRelationDto.id)).orElse(null)
                if (cycle != null) {
                    subjectPerCycleDao.save(SubjectPerCycle(
                        subject = newSubject,
                        cycle = cycle,
                        correlative = cycleRelationDto.correlative
                    ))
                }
            }
        }
    }

    override fun deleteAllListedSubjects(subjects: List<String>) {
        subjects.forEach {
            val subject = subjectDao.findById(it).orElse(null)
            if (subject != null) {
                subjectPerCycleDao.deleteAll(subjectPerCycleDao.findAllBySubject(subject))
                subjectDao.delete(subject)
            }
        }
    }

    override fun updateSubject(subject: SubjectDto) {
        val subjectToUpdate = subjectDao.findById(subject.code).orElseThrow {
            ExceptionNotFound("Subject with code ${subject.code} not found")
        }
        val newSubject = subjectToUpdate.copy(
            name = subject.name,
            uv = subject.uv,
            estimateGrade = subject.estimateGrade?: subjectToUpdate.estimateGrade
        )
        subjectDao.save(newSubject)

        subject.cycleRelation?.forEach {
            val cycle = cycleDao.findById(UUID.fromString(it.id)).orElse(null)
            val subPerCycle = subjectPerCycleDao.findByCorrelativeAndCycleAndSubject(
                it.correlative,
                cycle,
                subjectToUpdate
            )
            if (subPerCycle == null) {
                subjectPerCycleDao.save(SubjectPerCycle(
                    subject = subjectToUpdate,
                    cycle = cycle,
                    correlative = it.correlative
                ))
            }
        }
    }

    override fun deleteAllSubjects() {
        subjectPerCycleDao.deleteAll()
        subjectDao.deleteAll()
    }
}