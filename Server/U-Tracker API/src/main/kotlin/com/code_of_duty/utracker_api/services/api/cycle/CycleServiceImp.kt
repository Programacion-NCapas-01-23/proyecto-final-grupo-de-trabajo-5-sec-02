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
    private val prerequisitesDao: PrerequisitesDao,
    private val scheduleDao: ScheduleDao
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

                        StudentSubjectDto(
                            code = subject.code,
                            name = subject.name,
                            correlative = correlativeList[0],
                            uv = subject.uv,
                            prerequisiteID = prerequisiteIDs
                        )
                    }
                )
            } ?: emptyList()
        }
    }

    override fun createStudentCycle(studentCode: String, cycleType: Int, year: Int) {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val newStudentCycle = StudentCycle(
            cycleType = CycleType.values()[cycleType], // Assuming cycleType is an index for CycleType enum
            year = year,
            student = student
        )
        studentCycleDao.save(newStudentCycle)
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

    /*override fun findBestStudentCycle(
        studentCode: String,
        userCycleId: String,
        subjects: List<String>
    ): StudentCycleDto {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val studentUUID = student.code

        // Retrieve the schedules for the given subjects
        val schedules = getScheduleForSubjects(subjects)

        // If there are no schedules, return an empty cycle
        if (schedules.isEmpty()) {
            return StudentCycleDto(
                id = null,
                studentCode = studentCode,
                cycleType = 0, // Set the appropriate default cycleType value
                year = 0, // Set the appropriate default year value
                subjects = emptyList()
            )
        }

        // Create a solver.
        val solver = CpSolver()

        // Create the model.
        val model = CpModel()

        // Create variables.
        val subjectVariables = subjects.map { model.newBoolVar(it) }

        // Create constraints.
        for (i in subjects.indices) {
            for (j in i + 1 until subjects.size) {
                model.addBoolXor(listOf(subjectVariables[i], subjectVariables[j]))
            }
        }

        // Create constraints for schedules.
        for (i in schedules.indices) {
            val subjectIndex = subjects.indexOf(schedules[i].subject)
            if (subjectIndex != -1) {
                val subjectVariable = subjectVariables[subjectIndex]
                model.addBoolAnd(listOf(subjectVariable, model.newBoolVar(schedules[i].id.toString())))
            }
        }

        // Solve the problem.
        val status = solver.solve(model)

        // If a solution is found, check prerequisites and return the best cycle.
        if (status == CpSolverStatus.OPTIMAL) {
            val bestCycle = StudentCycleDto(
                id = null,
                studentCode = studentCode,
                cycleType = 0, // Set the appropriate default cycleType value
                year = 0, // Set the appropriate default year value
                subjects = subjectVariables.mapIndexedNotNull { index, variable ->
                    if (solver.booleanValue(variable)) subjects[index] else null
                }
            )

            return if (arePrerequisitesSatisfied(bestCycle.subjects, studentUUID)) {
                bestCycle
            } else {
                StudentCycleDto(
                    id = null,
                    studentCode = studentCode,
                    cycleType = 0, // Set the appropriate default cycleType value
                    year = 0, // Set the appropriate default year value
                    subjects = emptyList()
                )
            }
        } else {
            // No solution found.
            return StudentCycleDto(
                id = null,
                studentCode = studentCode,
                cycleType = 0, // Set the appropriate default cycleType value
                year = 0, // Set the appropriate default year value
                subjects = emptyList()
            )
        }
    }*/

    private fun getScheduleForSubjects(subjectId: List<String>): List<SchedulesDto> {
        val scheduleDao = scheduleDao
        // Retrieve the schedules for the given subjects from the database
        val schedules = scheduleDao.findBySubjectCodeIn(subjectId)

        // Convert the retrieved Schedule models to SchedulesDto objects
        val schedulesDtos = schedules.map { schedule ->
            SchedulesDto(
                id = schedule.id.toString(),
                collection = schedule.collection,
                subject = schedule.subject.name,
                classTime = schedule.classTime.id ?: UUID.randomUUID()
            )
        }
        return schedulesDtos
    }

    private fun arePrerequisitesSatisfied(subjectCodes: List<String>, studentCode: String): Boolean {
        val subjectPerStudentCycles = subjectPerStudentCycleDao.getSubjectStatusesForStudent(studentCode)

        for (subjectCode in subjectCodes) {
            val prerequisites = prerequisitesDao.getPrerequisitesForSubjects(listOf(subjectCode))

            for (prerequisite in prerequisites) {
                val prerequisiteSubjectCode = prerequisite.prerequisite.prerequisiteCode.subject.code

                val subjectStatus = subjectPerStudentCycles
                    .find { it.subject.code == prerequisiteSubjectCode }
                    ?.status

                if (subjectStatus != SubjectStatus.APPROVED) {
                    return false
                }
            }
        }

        return true
    }
}