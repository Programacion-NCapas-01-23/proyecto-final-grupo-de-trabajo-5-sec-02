package com.code_of_duty.u_tracker.ui.models

import com.code_of_duty.u_tracker.R

sealed class MainNavItems (
    val route: String,
    val name: String,
    val icon: Int)
{
    object Term : MainNavItems(
        name = "Ciclo",
        route = "term",
        icon = R.drawable.book_clock
    )

    object Pensum : MainNavItems(
        name = "Pensum",
        route = "pensum",
        icon = R.drawable.school
    )

    object Assesment : MainNavItems(
        name = "Evaluaciones",
        route = "assessment",
        icon = R.drawable.format_list_numbered
    )

    object Schedule : MainNavItems(
        name = "Horarios",
        route = "schedule",
        icon = R.drawable.table_clock
    )

    object CUM : MainNavItems(
        name = "CUM",
        route = "cum",
        icon = R.drawable.chart_sankey
    )
    object  Profile: MainNavItems(
        name = "Perfil",
        route = "profile",
        icon = R.drawable.account_circle
    )
}

