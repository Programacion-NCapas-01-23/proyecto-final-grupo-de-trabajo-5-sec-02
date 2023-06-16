package com.code_of_duty.utracker_api.services.admin.cycle

import com.code_of_duty.utracker_api.data.dao.CycleDao
import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dtos.CycleDto
import com.code_of_duty.utracker_api.data.enums.CycleType
import com.code_of_duty.utracker_api.data.models.Cycle
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminCycleServiceImpl(
    private val cycleDao: CycleDao,
    private val pensumDao: PensumDao,
) : AdminCycleService {
    override fun addAllCycles(cycles: List<CycleDto>) {
        cycles.forEach {
            val pensum =  pensumDao.findById(UUID.fromString(it.pensumId)).orElse(null)
            if (pensum != null) {
                val cycleType = CycleType.fromInt(it.type)
                val cycle = cycleDao.findByNameAndPensum(it.name, pensum)
                if (cycle == null) {
                    cycleDao.save(
                        Cycle(
                            cycleType = cycleType,
                            pensum = pensum,
                            name = it.name
                        )
                    )
                }
            }else{
                throw ExceptionNotFound("Pensum not found")
            }

        }
    }

    override fun deleteAllCycles(cycles: List<String>) {
        cycles.forEach {
            val cycle = cycleDao.findById(UUID.fromString(it)).orElse(null)
            if (cycle != null) {
                cycleDao.delete(cycle)
            }
        }
    }

    override fun updateCycle(cycle: CycleDto) {
        val cycleToUpdate = cycleDao.findById(UUID.fromString(cycle.id)).orElse(null)
        val pensum = pensumDao.findById(UUID.fromString(cycle.pensumId)).orElse(null)
        if (cycleToUpdate != null && pensum != null) {
            val newCycle = Cycle(
                id = cycleToUpdate.id,
                cycleType = CycleType.fromInt(cycle.type),
                pensum = pensum,
                name = cycle.name
            )
            cycleDao.save(newCycle)
        } else {
            throw ExceptionNotFound("Cycle not found")
        }
    }
}