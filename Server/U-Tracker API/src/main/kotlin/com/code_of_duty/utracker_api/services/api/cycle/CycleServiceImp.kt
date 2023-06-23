package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.data.models.Pensum
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.CpSolverStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class CycleServiceImp(
    private val cycleDao: CycleDao,
    private val studentDao: StudentDao,
    private val jwtUtils: JwtUtils,
) : CycleService {
    override fun getAllCycles(studentCode: String): List<CycleDto> {
        TODO()
    }
    override fun createStudentCycle(studentUUID: UUID, userCycleId: String, subjects: List<UUID>): StudentCycleDto {
        // Create a solver.
        val solver = CpSolver()

        // Create the model.
        val model = CpModel()

        // Create variables.
        val subjectVariables = subjects.map { model.newBoolVar(it.toString()) }

        // Create constraints.
        for (i in 0 until subjects.size) {
            for (j in i + 1 until subjects.size) {
                model.addBoolXor(listOf(subjectVariables[i], subjectVariables[j]))
            }
        }

        // Solve the problem.
        val status = solver.solve(model)

        // If a solution is found, return it.
        if (status == CpSolverStatus.OPTIMAL) {
            val bestCycle = StudentCycleDto(
                id = null,
                studentUUID = studentUUID,
                userCycleId = userCycleId,
                subjects = subjectVariables.mapIndexedNotNull { index, variable ->
                    if (solver.booleanValue(variable)) subjects[index] else null
                }
            )
            return bestCycle
        } else {
            // No solution found.
            return StudentCycleDto(
                id = null,
                studentUUID = studentUUID,
                userCycleId = userCycleId,
                subjects = emptyList()
            )
        }
    }

}