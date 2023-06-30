package com.code_of_duty.u_tracker.dummyData

import com.code_of_duty.u_tracker.enums.CycleType
import com.code_of_duty.u_tracker.ui.models.Cycle
import com.code_of_duty.u_tracker.ui.models.Subject


val pensum: List<Cycle> = listOf(
    Cycle(
        name = "Ciclo I",
        cycleType = CycleType.FIRST,
        orderValue = 1,
        subjects = listOf(
            Subject(
                code = ",",
                name = "precalculo",
                uv = 4,
                estimateGrade = 6.0f,
                prerequisiteID = null
            )
        )
    ),
    Cycle(
        name = "Ciclo II",
        cycleType = CycleType.SECOND,
        orderValue = 2,
        subjects = listOf(
            Subject(
                code = ",",
                name = "Calculo I",
                uv = 4,
                estimateGrade = 6.0f,
                prerequisiteID = listOf(1)
            )
        )
    )
)