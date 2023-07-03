package com.code_of_duty.u_tracker.dummyData

import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import com.code_of_duty.u_tracker.data.network.response.SubjectsFromTermResponse
import com.code_of_duty.u_tracker.enums.CycleType


val pensum: List<IdealTermResponse> = listOf(
    IdealTermResponse(
        name = "Ciclo I",
        cycleType = 1, /*TODO: SE CAMBIÓ EL TIPO POR INT*/
        orderValue = 1,
        subjects = listOf(
            SubjectsFromTermResponse(
                code = ",",
                name = "precalculo",
                uv = 4,
                estimateGrade = 6.0f,
                prerequisiteID = null
            )
        )
    ),
    IdealTermResponse(
        name = "Ciclo II",
        cycleType = 2,
        orderValue = 2,
        subjects = listOf(
            SubjectsFromTermResponse(
                code = ",",
                name = "Calculo I",
                uv = 4,
                estimateGrade = 6.0f,
                prerequisiteID = listOf(1)
            )
        )
    )
)