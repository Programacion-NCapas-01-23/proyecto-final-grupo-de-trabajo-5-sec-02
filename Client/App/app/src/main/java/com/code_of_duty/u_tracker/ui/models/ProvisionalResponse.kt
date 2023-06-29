package com.code_of_duty.u_tracker.ui.models

sealed class ProvisionalResponse (
    val id: String,
    val name: String,
    val faculty: String = "",
) {
    object CivilEngineering : ProvisionalResponse(
        id = "1",
        name = "Ingeniería Civil",
        faculty = "1"
    )
    object EnergeticEngineering : ProvisionalResponse(
        id = "2",
        name = "Ingeniería Energética",
        faculty = "1"
    )
    object IndustrialEngineering : ProvisionalResponse(
        id = "3",
        name = "Ingeniería Industrial",
        faculty = "1"

    )
    object MechanicalEngineering : ProvisionalResponse(
        id = "4",
        name = "Ingeniería Mecánica",
        faculty = "1"

    )
    object ElectricalEngineering : ProvisionalResponse(
        id = "5",
        name = "Ingeniería Eléctrica",
        faculty = "1"
    )
    object InformaticEngineering : ProvisionalResponse(
        id = "6",
        name = "Ingeniería Informática",
        faculty = "1"
    )
    object Arquitecure : ProvisionalResponse(
        id = "7",
        name = "Arquitectura",
        faculty = "1"
    )
}
