package com.code_of_duty.utracker_api.services.admin.subject

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.PrerequisitesDao
import com.code_of_duty.utracker_api.data.dao.SubjectDao
import com.code_of_duty.utracker_api.data.dao.SubjectPerCycleDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.CycleRelationDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.dtos.SubjectXPrerequisiteDto
import com.code_of_duty.utracker_api.data.models.*
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminSubjectServiceImpl(
    private val subjectDao: SubjectDao,
    private val cycleDao: CycleDao,
    private val subjectPerCycleDao: SubjectPerCycleDao,
    private val prerequisitesDao: PrerequisitesDao
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
                    val degreeMatches = degreeFilter == null || subjectPerCycle.cycle.pensum.degree.name.equals(degreeFilter, ignoreCase = true)
                    val pensumMatches = pensumFilter == null || subjectPerCycle.cycle.pensum.plan.equals(pensumFilter, ignoreCase = true)
                    val facultyMatches = facultyFilter == null || subjectPerCycle.cycle.pensum.degree.faculty?.name.equals(facultyFilter, ignoreCase = true)
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
                        pensumId = cycle.pensum.id.toString()
                    )
                )
            }

            SubjectDto(
                code = subject.code,
                name = subject.name,
                uv = subject.uv,
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
        subjects.forEach { subjectCode ->
            val subject = subjectDao.findById(subjectCode).orElse(null)
            if (subject != null) {
                // Retrieve the prerequisite entries for the subject
                val prerequisites = prerequisitesDao.findBySubjectCode(subjectCode)

                prerequisites.forEach { prerequisite ->
                    prerequisitesDao.delete(prerequisite)
                }

                // Clear the subject_x_cycle entries from the subject
                subject.subjectPerCycles.clear()
                subjectDao.save(subject)

                subjectDao.delete(subject)
            }
        }
    }

    override fun updateSubject(subject: SubjectDto) {
        val subjectToUpdate = subjectDao.findById(subject.code).orElseThrow {
            ExceptionNotFound("Subject with code ${subject.code} not found")
        }

        subjectToUpdate.name = subject.name
        subjectToUpdate.uv = subject.uv

        // Update subjectPerCycles if needed
        subject.cycleRelation?.forEach {
            val cycle = cycleDao.findById(UUID.fromString(it.id)).orElse(null)
            val subPerCycle = subjectPerCycleDao.findByCorrelativeAndCycleAndSubject(
                it.correlative,
                cycle,
                subjectToUpdate
            )
            if (subPerCycle != null) {
                subPerCycle.correlative = it.correlative
                subjectPerCycleDao.flush()
                subjectPerCycleDao.save(subPerCycle)
            }
        }

        subjectDao.flush()
        subjectDao.save(subjectToUpdate)
    }

    override fun deleteAllSubjects() {
        subjectPerCycleDao.deleteAll()
        subjectDao.deleteAll()
    }

    override fun addPrerequisites(subjects: List<SubjectXPrerequisiteDto>) {
        subjects.forEach { subjectDto ->
            val subject = subjectDao.findById(subjectDto.subjectCode).orElse(null)

            if (subject != null) {
                subjectDto.prerequisites.forEach { prerequisiteCode ->
                    val prerequisiteSubjectPerCycle = subjectPerCycleDao.findBySubject(prerequisiteCode)

                    if (prerequisiteSubjectPerCycle != null) {
                        val newPrerequisite = Prerequisite(
                            prerequisite = PrerequisiteID(subjectCode = subject, prerequisiteCode = prerequisiteSubjectPerCycle)
                        )
                        prerequisitesDao.save(newPrerequisite)
                    }
                }
            }
        }
    }

}