package com.code_of_duty.utracker_api.services.api.subject

import com.code_of_duty.utracker_api.data.dao.AssesmentDao
import com.code_of_duty.utracker_api.data.dao.SubjectDao
import com.code_of_duty.utracker_api.data.dtos.AssesmentDto
import com.code_of_duty.utracker_api.data.models.Assessment
import com.code_of_duty.utracker_api.data.models.Subject
import jakarta.persistence.EntityNotFoundException
import org.hibernate.validator.constraints.UUID
import org.springframework.stereotype.Component

@Component
class SubjectServiceImp(private val subjectDao: SubjectDao, private val assessmentDao: AssesmentDao) : SubjectService {

    override fun getAllSubjects(code: String): List<Subject> {
        return subjectDao.findByCode(code)
    }

    override fun getSubjectByName(name: String): Subject? {
        return subjectDao.findByName(name)
    }

    override fun getSubjectsByStudent(studentId: String): List<Subject> {
        return subjectDao.findByStudentId(studentId)
    }

    override fun setAssessment(uuid: UUID, assessmentDto: AssesmentDto): Subject {
        val subject = subjectDao.findById(uuid)
            .orElseThrow { EntityNotFoundException("Subject not found with id: $uuid") }
        val assessment = Assessment(
            name = assessmentDto.name,
            percentage = assessmentDto.percentage,
            date = assessmentDto.date,
            grade = assessmentDto.grade,
            subject = subject
        )
        assessmentDao.save(assessment)
        return subject
    }


    override fun calculateEstimateGrades(code: String, assessments: List<Assessment>): Subject {
        val subject = subjectDao.findByCode(code)

        var total = 0.0
        var totalPercentage = 0
        var totalAssessments = 0

        for (assessment in assessments) {
            if (assessment.grade != null) {
                // If the assessment has a grade, add it to the total
                total += assessment.grade * assessment.percentage
            } else {
                // If the assessment doesn't have a grade, add the percentage to the total percentage
                totalPercentage += assessment.percentage
            }
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

        if (remainingGrade <= 0) {
            // If the remaining grade is zero or negative, mark the subject as passed
            subject[0].passed = true
        } else if (totalAssessments > 0) {
            // Calculate the grade needed in the remaining assessments
            val gradeNeeded = remainingGrade * totalAssessments - total
            subject[0].remainingGrade = gradeNeeded / totalAssessments
        }

        return subject[0]
    }



}