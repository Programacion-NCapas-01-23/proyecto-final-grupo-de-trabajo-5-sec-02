package com.code_of_duty.u_tracker.ui.models

sealed class ProvisionalFaculty(
    val id: String,
    val name: String,
) {
    object Engineering: ProvisionalFaculty(
        id = "1",
        name = "Ingeniería y Arquitectura"
    )

    object Humanities: ProvisionalFaculty(
        id = "2",
        name = "Ciencias Sociales y Humanidades"
    )

    object Law: ProvisionalFaculty(
        id = "3",
        name = "Derecho"
    )

    object Economics: ProvisionalFaculty(
        id = "4",
        name = "Gestión Empresarial y Negocios"
    )

    object Education: ProvisionalFaculty(
        id = "5",
        name = "Educación"
    )

    object Communications: ProvisionalFaculty(
        id = "6",
        name = "Comunicaciones y Mercadeo"
    )
}


