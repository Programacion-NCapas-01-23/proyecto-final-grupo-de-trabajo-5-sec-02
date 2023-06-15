package com.code_of_duty.u_tracker.ui.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.code_of_duty.u_tracker.ui.models.BottomNavItems
import com.code_of_duty.u_tracker.ui.screens.ScreenContent
import com.code_of_duty.u_tracker.ui.screens.assesment.AssesmentScreen
import com.code_of_duty.u_tracker.ui.screens.cum.CUMScreen
import com.code_of_duty.u_tracker.ui.screens.pensum.PensumScreen
import com.code_of_duty.u_tracker.ui.screens.schedule.ScheduleScreen
import com.code_of_duty.u_tracker.ui.screens.term.TermScreen

@Composable
fun HomeBottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItems.Term.route
    ) {
        composable(route = BottomNavItems.Schedule.route){
            ScheduleScreen()
        }
        composable(route = BottomNavItems.Assesment.route){
            ScreenContent(
                name = BottomNavItems.Assesment.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = BottomNavItems.CUM.route){
            CUMScreen()
        }
        composable(route = BottomNavItems.Term.route){
            TermScreen()
        }
        composable(route = BottomNavItems.Pensum.route){
            PensumScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route){
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}
