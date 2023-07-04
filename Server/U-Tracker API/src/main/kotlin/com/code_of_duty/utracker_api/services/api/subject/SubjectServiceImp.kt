package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dao.*
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.CycleRelationDto
import com.code_of_duty.utracker_api.data.dtos.RemainingAssessmentDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component
class SubjectServiceImp(
    private val subjectDao: SubjectDao,
    private val assessmentDao: AssesmentDao,
    private val subjectPerStudentCycleDao: SubjectPerStudentCycleDao,
    private val cycleDao: CycleDao,
    private val studentCycleDao: StudentCycleDao
) : SubjectService {

    override fun getAllSubjects(
        nameFilter: String?,
        sortBy: String?,
        degreeFilter: String?,
        pensumFilter: String?,
        facultyFilter: String?
    ): List<SubjectDto> {
        val subjects = subjectDao.findAllWithSubjectPerCycles()

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

    override fun updateSubjectCompletion(studentCode: String, subjectCode: String, state: SubjectStatus, grade: BigDecimal?): Subject {
        val studentCycles = studentCycleDao.findByStudentCode(studentCode)

        var matchingSubjectPerStudentCycle: SubjectPerStudentCycle? = null

        for (studentCycle in studentCycles) {
            matchingSubjectPerStudentCycle = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCycle(subjectCode, studentCycle)
            if (matchingSubjectPerStudentCycle != null) {
                break
            }
        }

        if (matchingSubjectPerStudentCycle == null) {
            throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode and student code: $studentCode")
        }

        matchingSubjectPerStudentCycle.status = state

        if (grade != null) {
            matchingSubjectPerStudentCycle.grade = grade
        }

        // Save the updated SubjectPerStudentCycle entity
        subjectPerStudentCycleDao.save(matchingSubjectPerStudentCycle)

        return matchingSubjectPerStudentCycle.subject
    }

    override fun setAssessment(subjectCode: String, name: String, percentage: Int, date: LocalDate, grade: BigDecimal?): Subject {
        val subjectPerStudentCycleList = subjectPerStudentCycleDao.findBySubjectCode(subjectCode)
        val subjectPerStudentCycle = subjectPerStudentCycleList.firstOrNull()
            ?: throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode")

        val assessment = Assessment(
            name = name,
            percentage = percentage,
            date = date,
            grade = grade,
            subjectPerStudentCycle = subjectPerStudentCycle
        )
        assessmentDao.save(assessment)

        return subjectPerStudentCycle.subject
    }

    override fun calculateEstimateGrades(subjectCode: String): List<RemainingAssessmentDto> {
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findBySubjectCode(subjectCode)
            .firstOrNull() ?: throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode")

        val assessments = assessmentDao.findBySubjectPerStudentCycle(subjectPerStudentCycle)
        val estimateGrade = subjectPerStudentCycleDao.getEstimateGradeBySubjectCode(subjectCode)

        val totalPercentageAchieved = assessments.fold(BigDecimal.ZERO) { acc, assessment ->
            acc + (assessment.grade?.multiply(assessment.percentage.toBigDecimal()) ?: BigDecimal.ZERO)
        }

        val remainingPercentageNeeded = estimateGrade?.minus(totalPercentageAchieved.toDouble()) ?: BigDecimal.ZERO


        if (estimateGrade != null && totalPercentageAchieved >= estimateGrade.toBigDecimal()) {
            // Already passed the subject
            return listOf(RemainingAssessmentDto("You already passed the subject", null))
        }

        val remainingAssessments = assessments.filter { it.grade == null }
        val remainingTotalPercentage = remainingAssessments.sumOf { it.percentage }

        val estimateGrades = remainingAssessments.map { assessment ->
            val percentageNeeded = (assessment.percentage.toDouble() / remainingTotalPercentage) * remainingPercentageNeeded.toDouble()
            val gradeNeeded = (percentageNeeded / assessment.percentage.toDouble()) * 100.0
            RemainingAssessmentDto(assessment.name, gradeNeeded.toBigDecimal())
        }

        return estimateGrades
    }

}