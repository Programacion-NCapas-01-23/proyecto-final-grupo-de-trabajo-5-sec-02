package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dao.*
import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.data.enums.CycleType
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.*
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.util.*

@Component
class CycleServiceImp(
    private val subjectPerStudentCycleDao: SubjectPerStudentCycleDao,
    private val studentCycleDao: StudentCycleDao,
    private val studentDao: StudentDao,
    private val subjectDao: SubjectDao,
    private val prerequisitesDao: PrerequisitesDao
) : CycleService {
    override fun getAllCycles(studentCode: String): List<StudentCycleResponseDto> {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val pensums: Iterable<Pensum> = student.degree?.pensums ?: emptyList()
        val prerequisites: List<Prerequisite> = prerequisitesDao.findAll()

        return pensums.flatMap { pensum ->
            pensum.cycles?.map { cycle ->
                StudentCycleResponseDto(
                    name = cycle.name,
                    cycleType = cycle.cycleType.ordinal,
                    orderValue = cycle.orderValue,
                    subjects = cycle.subjects.map { subject ->
                        val subjectCode = subject.code
                        val prerequisitesForSubject = prerequisites.filter { it.prerequisite.subjectCode.code == subjectCode }
                        val prerequisiteIDs = prerequisitesForSubject.map { it.prerequisite.prerequisiteCode.correlative }
                        val correlativeList = subject.subjectPerCycles.map { it.correlative }

                        val subjectPerStudentCycleList = subjectPerStudentCycleDao.findBySubjectCodeAndStudentCode(subjectCode, studentCode)
                        val grade = subjectPerStudentCycleList.firstOrNull()?.grade

                        StudentSubjectDto(
                            code = subject.code,
                            name = subject.name,
                            correlative = correlativeList[0],
                            uv = subject.uv,
                            grade = grade,
                            prerequisiteID = prerequisiteIDs
                        )
                    }
                )
            } ?: emptyList()
        }
    }



    override fun createStudentCycle(studentCode: String, cycleType: Int, year: Int): StudentCycleCreatedDto {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val newStudentCycle = StudentCycle(
            cycleType = CycleType.values()[cycleType], // Assuming cycleType is an index for CycleType enum
            year = year,
            student = student
        )
        val savedStudentCycle = studentCycleDao.save(newStudentCycle)

        return StudentCycleCreatedDto(
            studentCycleId = savedStudentCycle.studentCycleId.toString(),
            cycleType = savedStudentCycle.cycleType.ordinal,
            year = savedStudentCycle.year
        )
    }


    override fun addSubjectToStudentPerCycle(studentCycleId: UUID, subjectCode: String, estimateGrade: BigDecimal?) {
        val studentCycle = studentCycleDao.findById(studentCycleId)
            .orElseThrow { ExceptionNotFound("Student cycle not found") }

        val subject = subjectDao.findByCode(subjectCode)
            ?: throw ExceptionNotFound("Subject not found")

        // Check if the subject is already associated with the student cycle
        val existingSubjectPerStudentCycle = subjectPerStudentCycleDao
            .findByStudentCycleAndSubject(studentCycle, subject)

        if (existingSubjectPerStudentCycle != null) {
            throw IllegalArgumentException("The subject is already added")
        }

        // Check if the prerequisites are approved
        val prerequisites = prerequisitesDao.findBySubjectCode(subjectCode)
        val prerequisiteStatuses = prerequisites.map { prerequisite ->
            subjectPerStudentCycleDao.findByStudentCycleAndSubject(studentCycle, prerequisite.prerequisite.prerequisiteCode.subject)
                ?.status ?: SubjectStatus.APPROVED
        }

        if (!prerequisiteStatuses.all { it == SubjectStatus.REJECTED }) {
            throw IllegalArgumentException("Prerequisites are not approved")
        }

        // Create a new SubjectPerStudentCycle entity
        val subjectPerStudentCycle = SubjectPerStudentCycle(
            status = SubjectStatus.IN_PROGRESS,
            grade = BigDecimal.ZERO,
            estimateGrade = estimateGrade,
            studentCycle = studentCycle,
            subject = subject
        )
        subjectPerStudentCycleDao.save(subjectPerStudentCycle)
    }


    @Transactional
    override fun removeSubjectFromStudentPerCycle(studentCycleId: UUID, subjectCode: String) {
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findByStudentCycleAndSubjectCode(studentCycleId, subjectCode)
            ?: throw ExceptionNotFound("Subject not found in student cycle")
        subjectPerStudentCycleDao.delete(subjectPerStudentCycle)
    }

    override fun getStudentCycles(studentCode: String): List<StudentCycleDto> {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val studentCycles = student.studentCycles ?: emptyList()

        return studentCycles.map { studentCycle ->
            val subjects = studentCycle.subjects.map { subject ->
                val prerequisiteID = subject.subjectPerCycles.map { it.correlative }
                StudentSubjectXCycleDto(
                    code = subject.code,
                    name = subject.name,
                    uv = subject.uv,
                    prerequisiteID = prerequisiteID // Set prerequisites if needed
                )
            }

            StudentCycleDto(
                id = studentCycle.studentCycleId.toString(),
                studentCode = studentCode,
                cycleType = studentCycle.cycleType.ordinal,
                year = studentCycle.year,
                subjects = subjects.map { it.code } // Map subjects to their codes
            )
        }
    }

    override fun deleteStudentCycle(studentCode: String, studentCycleId: UUID) {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val studentCycle = student.studentCycles?.find { it.studentCycleId == studentCycleId }
            ?: throw ExceptionNotFound("Student cycle not found")

        // Delete the studentCycle entity from the database
        studentCycleDao.delete(studentCycle)
    }

}