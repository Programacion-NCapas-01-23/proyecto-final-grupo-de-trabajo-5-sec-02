package com.code_of_duty.utracker_api.services.api.cycle

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.dtos.StudentCycleDto
import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.CpSolverStatus
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CycleServiceImp(private val cycleDao: CycleDao, private val pensumDao: PensumDao, private val degreeDao: DegreeDao) : CycleService {
    override fun getAllCycles(userDegree: UUID): List<CycleDto> {
        // Retrieve the pensum based on the user's degree
        val degree = degreeDao.findByCode(userDegree)
            ?: throw RuntimeException("Degree not found for code: $userDegree")

        val pensum = pensumDao.findByPlanAndDegree("default", degree)
            ?: throw RuntimeException("Pensum not found for degree: $userDegree")

        // Retrieve cycles based on the pensum
        val cycles = cycleDao.findByPensum(pensum)

        // Map the cycles to DTOs
        return cycles.map { cycle ->
            CycleDto(
                id = cycle.id.toString(),
                type = cycle.cycleType.ordinal,
                name = cycle.name,
                pensumId = cycle.pensum.id.toString()
            )
        }
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