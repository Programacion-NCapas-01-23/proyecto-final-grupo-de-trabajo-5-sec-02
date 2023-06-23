package com.code_of_duty.utracker_api.services.api.degree

import com.code_of_duty.utracker_api.data.dao.DegreeDao
import org.springframework.stereotype.Component
import java.util.*

@Component
class DegreeServiceImp (private val degreeDao: DegreeDao) : DegreeService {
    override fun findById(id: UUID) = degreeDao.findById(id).orElse(null)
}