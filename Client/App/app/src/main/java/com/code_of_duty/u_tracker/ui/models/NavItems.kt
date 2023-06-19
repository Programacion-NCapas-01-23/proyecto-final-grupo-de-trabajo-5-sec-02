package com.code_of_duty.u_tracker.ui.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import com.code_of_duty.u_tracker.R

sealed class NavItems (
    val route: String,
    val name: String,
    val icon: Int)
{
    object Term : NavItems(
        name = "Ciclo",
        route = "term",
        icon = R.drawable.book_clock
    )

    object Pensum : NavItems(
        name = "Pensum",
        route = "pensum",
        icon = R.drawable.school
    )

    object Assesment : NavItems(
        name = "Evaluaciones",
        route = "assessment",
        icon = R.drawable.format_list_numbered
    )

    object Schedule : NavItems(
        name = "Horarios",
        route = "schedule",
        icon = R.drawable.table_clock
    )

    object CUM : NavItems(
        name = "CUM",
        route = "cum",
        icon = R.drawable.chart_sankey
    )
    object  Profile: NavItems(
        name = "Perfil",
        route = "profile",
        icon = R.drawable.account_circle
    )

    object Logout: NavItems(
        name = "Logout",
        route = "logout",
        icon = 1
    )
}

