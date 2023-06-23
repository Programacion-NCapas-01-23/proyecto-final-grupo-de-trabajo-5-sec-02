package com.code_of_duty.utracker_api.services.api.degree

import com.code_of_duty.utracker_api.data.dao.DegreeDao
import com.code_of_duty.utracker_api.data.models.Degree
import org.springframework.stereotype.Component
import java.util.*

@Component
class DegreeServiceImp (private val degreeDao: DegreeDao) : DegreeService {
    override fun getAllCycles(userDegree: UUID): Optional<Degree> {
        return degreeDao.findById(userDegree)
    }

    override fun findById(id: UUID) = degreeDao.findById(id).orElse(null)
}