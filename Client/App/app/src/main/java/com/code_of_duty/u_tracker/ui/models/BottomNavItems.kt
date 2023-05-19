package com.code_of_duty.u_tracker.ui.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.code_of_duty.u_tracker.R
import androidx.compose.ui.res.vectorResource

sealed class BottomNavItems (
    val route: String,
    val name: String,
    val icon: Int)
{
    object Term : BottomNavItems(
        name = "Term",
        route = "term",
        icon = R.drawable.book_clock
    )

    object Pensum : BottomNavItems(
        name = "Pensum",
        route = "pensum",
        icon = R.drawable.school
    )

    object Assesment : BottomNavItems(
        name = "Assessment",
        route = "assessment",
        icon = R.drawable.format_list_numbered
    )

    object Schedule : BottomNavItems(
        name = "Schedule",
        route = "schedule",
        icon = R.drawable.table_clock
    )

    object CUM : BottomNavItems(
        name = "CUM",
        route = "cum",
        icon = R.drawable.chart_sankey
    )
}