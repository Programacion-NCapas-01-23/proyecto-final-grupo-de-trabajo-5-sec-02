package com.code_of_duty.u_tracker.ui.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.ui.screens.assesment.grade.GradeScreen
import com.code_of_duty.u_tracker.ui.screens.assesment.percentage.PercentageScreen

sealed class TabItem(
    val title: String,
    val screen: @Composable () -> Unit,
    val icon: Int,
){
    object Percentages: TabItem(
        title = "Porcentaje",
        screen = { PercentageScreen() },
        icon = R.drawable.percent
    )

    object Grades: TabItem(
        title = "Notas",
        screen = { GradeScreen() },
        icon = R.drawable.grade
    )
}

