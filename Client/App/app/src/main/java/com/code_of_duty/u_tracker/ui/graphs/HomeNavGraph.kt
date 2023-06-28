package com.code_of_duty.u_tracker.ui.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.code_of_duty.u_tracker.ui.models.MainNavItems
import com.code_of_duty.u_tracker.ui.screens.ScreenContent
import com.code_of_duty.u_tracker.ui.screens.cum.CUMScreen
import com.code_of_duty.u_tracker.ui.screens.pensum.PensumScreen
import com.code_of_duty.u_tracker.ui.screens.schedule.ScheduleScreen
import com.code_of_duty.u_tracker.ui.screens.term.TermScreen
import com.code_of_duty.u_tracker.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation(
        route = Graph.HOME,
        startDestination = MainNavItems.Term.route
    ) {
        composable(route = MainNavItems.Schedule.route){
            ScheduleScreen()
        }
        composable(route = MainNavItems.Assesment.route){
            ScreenContent(
                name = MainNavItems.Assesment.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = MainNavItems.CUM.route){
            CUMScreen()
        }
        composable(route = MainNavItems.Term.route){
            TermScreen()
        }
        composable(route = MainNavItems.Pensum.route){
            PensumScreen()
        }
        composable(route = MainNavItems.Profile.route){
            ProfileScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavController) {
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
