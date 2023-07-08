package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dao.*
import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Component
class SubjectServiceImp(
    private val subjectDao: SubjectDao,
    private val assessmentDao: AssessmentDao,
    private val subjectPerStudentCycleDao: SubjectPerStudentCycleDao,
    private val cycleDao: CycleDao,
    private val studentCycleDao: StudentCycleDao,
    private val studentDao: StudentDao
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

    override fun getAllAssessments(studentCode: String, subjectCode: String): List<AssessmentResponseDto> {
        val subjectPerStudentCycleList = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCode(subjectCode, studentCode)
        val subjectPerStudentCycle = subjectPerStudentCycleList.firstOrNull()
            ?: throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode")

        val assessments = assessmentDao.findBySubjectPerStudentCycle(subjectPerStudentCycle)

        return assessments.map { assessment ->
            AssessmentResponseDto(
                assessmentId = assessment.id.toString(),
                name = assessment.name,
                percentage = assessment.percentage,
                date = assessment.date,
                grade = assessment.grade
            )
        }
    }

    override fun setAssessment(studentCode:String,subjectCode: String, name: String, percentage: Int, date: LocalDate, grade: BigDecimal?): Subject {
        val subjectPerStudentCycleList = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCode(subjectCode, studentCode)
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

    override fun updateAssessment(studentCode: String, assessmentId: UUID, name: String?, percentage: Int?, date: LocalDate?, grade: BigDecimal?): Subject{
        val assessment = assessmentDao.findByIdAndStudentCode(assessmentId, studentCode)

        assessment.name = name?: assessment.name
        assessment.percentage = percentage?: assessment.percentage
        assessment.date = date?: assessment.date
        assessment.grade = grade

        assessmentDao.save(assessment)

        return assessment.subjectPerStudentCycle.subject
    }

    override fun deleteAssessment(studentCode: String, assessmentId: UUID): Subject {
        val assessment = assessmentDao.findByIdAndStudentCode(assessmentId, studentCode)

        assessmentDao.delete(assessment)

        return assessment.subjectPerStudentCycle.subject
    }

    override fun calculateEstimateGrades(studentCode:String,subjectCode: String): List<RemainingAssessmentDto> {
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCode(subjectCode, studentCode)
            .firstOrNull() ?: throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode")

        val assessments = assessmentDao.findBySubjectPerStudentCycle(subjectPerStudentCycle)
        val estimateGrade = subjectPerStudentCycleDao.getEstimateGradeBySubjectCode(subjectCode)

        val totalPercentageAchieved = assessments.fold(BigDecimal.ZERO) { acc, assessment ->
            acc + (assessment.grade?.multiply(assessment.percentage.toBigDecimal().divide(BigDecimal(100))) ?: BigDecimal.ZERO)
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
            val gradeNeeded = (percentageNeeded / (assessment.percentage.toDouble() / 100.0)) * 10.0 // Cap at 10
            val cappedGradeNeeded = if (gradeNeeded > 10) 10.0 else gradeNeeded // Cap at 10
            RemainingAssessmentDto(assessment.name, cappedGradeNeeded.toBigDecimal())
        }
        return estimateGrades
    }

    override fun calculateRemainingGradeToPass(studentCode: String,subjectCode: String): PassGradeDto {
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCode(subjectCode, studentCode)
            .firstOrNull() ?: throw EntityNotFoundException("SubjectPerStudentCycle not found for subject code: $subjectCode")

        val assessments = assessmentDao.findBySubjectPerStudentCycle(subjectPerStudentCycle)

        val actualGrade = assessments.fold(BigDecimal.ZERO) { acc, assessment ->
            acc + (assessment.grade?.multiply(assessment.percentage.toBigDecimal().divide(BigDecimal(100))) ?: BigDecimal.ZERO)
        }

        val gradeRemaining = BigDecimal("6.0") - actualGrade

        val message = if (actualGrade >= BigDecimal("6.0")) {
            "You already passed the subject"
        } else {
            "You need to get $gradeRemaining to pass the subject"
        }

        return PassGradeDto(actualGrade, gradeRemaining, message)
    }

    override fun calculateCum(studentCode: String): CumDto {
        val subjectPerStudentCycles = subjectPerStudentCycleDao.findByStudentCode(studentCode)
        var totalMeritUnits = BigDecimal.ZERO
        var totalValueUnits = BigDecimal.ZERO

        for (subjectPerStudentCycle in subjectPerStudentCycles) {
            val grade = subjectPerStudentCycle.grade ?: BigDecimal.ZERO
            val valueUnits = subjectPerStudentCycle.subject.uv

            val meritUnits = grade * valueUnits.toBigDecimal()
            totalMeritUnits += meritUnits
            totalValueUnits += valueUnits.toBigDecimal()
        }

        val cum = if (totalValueUnits != BigDecimal.ZERO) {
            totalMeritUnits / totalValueUnits
        } else {
            BigDecimal.ZERO
        }

        return CumDto(cum)
    }


}