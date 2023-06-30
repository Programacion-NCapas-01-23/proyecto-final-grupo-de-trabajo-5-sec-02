package com.code_of_duty.utracker_api.services.admin.pensum

import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.dao.PensumDao
import com.code_of_duty.utracker_api.data.dtos.PensumDto
import com.code_of_duty.utracker_api.data.models.Pensum
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminPensumServiceImpl(
    private val pensumDao: PensumDao,
    private val degreeDao: DegreeDao
) : AdminPensumService{

    override fun getAllPensums(): List<PensumDto> {
        return pensumDao.findAll().map {
            PensumDto(
                id = it.id.toString(),
                plan = it.plan,
                degreeId = it.degree.id.toString()
            )
        }
    }
    override fun addAllPensums(pensums: List<PensumDto>) {
        pensums.forEach {
            val degree = degreeDao.findById(UUID.fromString(it.degreeId))
                .orElseThrow { ExceptionNotFound("Degree not found") }

            val pensum = pensumDao.findByPlanAndDegree(it.plan, degree)

            if (pensum == null) {
                val newPensum = Pensum(
                    plan = it.plan,
                    degree = degree,
                )
                pensumDao.save(newPensum)
            }
        }
    }

    override fun deleteAllListedPensums(ids: List<String>) {
        ids.forEach {
            val pensum = pensumDao.findById(UUID.fromString(it)).orElse(null)

            if (pensum != null) {
                pensumDao.delete(pensum)
            }
        }
    }

    override fun updatePensum(pensum: PensumDto) {
        val degree = degreeDao.findById(UUID.fromString(pensum.degreeId))
            .orElseThrow { ExceptionNotFound("Degree not found") }

        pensumDao.findById(UUID.fromString(pensum.id))
            .orElseThrow { ExceptionNotFound("Pensum not found") }

        val pensumToUpdate = Pensum(
            id = UUID.fromString(pensum.id),
            plan = pensum.plan,
            degree = degree,
        )

        pensumDao.save(pensumToUpdate)
    }
}