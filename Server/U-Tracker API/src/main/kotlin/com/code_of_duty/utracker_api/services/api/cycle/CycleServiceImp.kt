package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dao.*
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.SchedulesDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.data.dtos.SubjectDto
import com.code_of_duty.utracker_api.data.enums.CycleType
import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.code_of_duty.utracker_api.data.models.Pensum
import com.code_of_duty.utracker_api.data.models.StudentCycle
import com.code_of_duty.utracker_api.data.models.Subject
import com.code_of_duty.utracker_api.data.models.SubjectPerStudentCycle
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.CpSolverStatus
import org.springframework.stereotype.Component
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
    override fun getAllCycles(studentCode: String): List<CycleDto> {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")
        val pensums: Iterable<Pensum> = student.degree?.pensums ?: emptyList()
        return pensums.flatMap { pensum ->
            pensum.cycles?.map { cycle ->
                CycleDto(
                    id = cycle.id.toString(),
                    name = cycle.name,
                    type = cycle.cycleType.ordinal,
                    pensumId = pensum.id.toString(),
                    subjects = cycle.subjects.map { subject ->
                        com.code_of_duty.utracker_api.data.dtos.SubjectDto(
                            code = subject.code,
                            name = subject.name,
                            uv = subject.uv,
                            estimateGrade = subject.estimateGrade,
                            cycleRelation = null
                        )
                    }
                )
            } ?: emptyList()
        }
    }

    override fun createStudentCycle(studentCode: String, cycleType: Int, year: Int) {
        val student = studentDao.findByCode(studentCode) ?: throw ExceptionNotFound("Student not found")

        // Create a new student cycle with the provided cycle type, year, and student
        val newStudentCycle = StudentCycle(
            cycleType = CycleType.values()[cycleType], // Assuming cycleType is an index for CycleType enum
            year = year,
            student = student,
            subjects = emptyList() // Initialize with an empty list of subjects for now
        )

        studentCycleDao.save(newStudentCycle)
    }

    override fun addSubjectToStudentCycle(studentCode: String, studentCycleId: UUID, subjectCode: String) {
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findByStudentCycleAndSubjectCode(studentCycleId, subjectCode)
        val subject = subjectDao.findByCode(subjectCode) ?: throw ExceptionNotFound("Subject not found")

        if (subjectPerStudentCycle == null) {
            // Get the student cycle
            val studentCycle = getStudentCycleById(studentCycleId)

            // Create a new SubjectPerStudentCycle object
            val newSubjectPerStudentCycle = SubjectPerStudentCycle(
                subject = subject,
                studentCycle = studentCycle,
                status = SubjectStatus.PENDING
            )

            // Add the subject to the student cycle
            studentCycle.subjects = studentCycle.subjects + newSubjectPerStudentCycle

            // Save the updated student cycle
            studentCycleDao.save(studentCycle)
        } else {
            throw ExceptionNotFound("Subject already added to the student cycle")
        }
    }


    override fun removeSubjectFromStudentCycle(studentCode: String, studentCycleId: UUID, subjectCode: String) {
        val studentCycle = getStudentCycleById(studentCycleId)
        val subjectPerStudentCycle = subjectPerStudentCycleDao.findByStudentCycleAndSubjectCode(studentCycleId, subjectCode)

        if (subjectPerStudentCycle != null) {
            // Remove the subject from the student cycle
            studentCycle.subjects = studentCycle.subjects.filter { it.code != subjectCode }

            // Delete the SubjectPerStudentCycle
            subjectPerStudentCycleDao.delete(subjectPerStudentCycle)

            // Save the updated student cycle
            studentCycleDao.save(studentCycle)
        } else {
            throw ExceptionNotFound("Subject not found in the student cycle")
        }
    }

    override fun getStudentCycle(studentCode: String, studentCycleId: UUID): StudentCycleDto {
        val studentCycle = getStudentCycleById(studentCycleId)

        // Create a StudentCycleDto from the retrieved StudentCycle object
        return StudentCycleDto(
            id = studentCycleId.toString(),
            studentCode = studentCode,
            cycleType = studentCycle.cycleType.ordinal,
            year = studentCycle.year,
            subjects = studentCycle.subjects.map { it.code }
        )
    }

    override fun deleteStudentCycle(studentCode: String, studentCycleId: UUID) {
        val studentCycle = getStudentCycleById(studentCycleId)

        // Delete the student cycle
        studentCycleDao.delete(studentCycle)
    }

    override fun findBestStudentCycle(
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
        val subjectVariables = subjects.map { model.newBoolVar(it.toString()) }

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
    }


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

    private fun arePrerequisitesSatisfied(subjects: List<String>?, studentCode: String): Boolean {
        val subjectPerStudentCycles = subjectPerStudentCycleDao.getSubjectStatusesForStudent(studentCode)

        if (subjects != null) {
            for (subjectCode in subjects) {
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
        }
        return true
    }
}