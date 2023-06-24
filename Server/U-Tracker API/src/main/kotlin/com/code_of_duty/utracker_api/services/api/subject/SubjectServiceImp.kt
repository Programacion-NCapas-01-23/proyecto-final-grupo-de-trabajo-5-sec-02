package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dao.AssesmentDao
import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.SubjectDao
import com.code_of_duty.utracker_api.data.dtos.AssesmentDto
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.CycleRelationDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import jakarta.persistence.EntityNotFoundException
import org.hibernate.validator.constraints.UUID
import org.springframework.stereotype.Component

@Component
class SubjectServiceImp(
    private val subjectDao: SubjectDao,
    private val assessmentDao: AssesmentDao,
    private val cycleDao: CycleDao
) : SubjectService {

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
                        pensumId = cycle.pensum.id.toString(),
                        subjects = null
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

    override fun updateSubjectCompletion(uuid: String, subjectCode: String, completed: Boolean): Subject {
        TODO()
    }

    override fun setAssessment(uuid: UUID, assessmentDto: AssesmentDto): Subject {
        TODO("Not yet implemented")
//        val subject = subjectDao.findById(uuid)
//            .orElseThrow { EntityNotFoundException("Subject not found with id: $uuid") }
//        val assessment = Assessment(
//            name = assessmentDto.name,
//            percentage = assessmentDto.percentage,
//            date = assessmentDto.date,
//            grade = assessmentDto.grade,
//            subject = subject
//        )
//        assessmentDao.save(assessment)
//        return subject
    }


    override fun calculateEstimateGrades(code: String, assessment: List<Assessment>): List<Double> {
        val subject = subjectDao.findByCode(code)

        var total = 0.0
        var totalPercentage = 0
        var totalAssessments = 0

        for (assessment in assessment) {
            total += assessment.grade * assessment.percentage
            totalAssessments++
        }

        val estimateGrade = if (totalPercentage != 0) {
            // Calculate the estimate grade if there are assessments without grades
            total / totalPercentage
        } else {
            // If all assessments have grades, use the total as the estimate grade
            total
        }

        val passingGrade = 6.0
        val remainingGrade = passingGrade - estimateGrade

        val gradeNeededPerAssessment = mutableListOf<Double>()

        if (remainingGrade > 0) {
            // Calculate the grade needed in the remaining assessments
            val remainingAssessments = assessment.filter { false }
            val totalAssessments = remainingAssessments.size

            if (totalAssessments > 0) {
                val gradeNeeded = remainingGrade * totalAssessments
                val remainingGradeNeeded = gradeNeeded / totalAssessments

                for (i in 1..totalAssessments) {
                    gradeNeededPerAssessment.add(remainingGradeNeeded)
                }
            }
        }
        return gradeNeededPerAssessment
    }

}