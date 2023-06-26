package com.code_of_duty.u_tracker.ui.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code_of_duty.u_tracker.ui.models.AuthNavItems
import com.code_of_duty.u_tracker.ui.screens.forgotPassword.getVerificationToken.GetVerificationTokenScreen
import com.code_of_duty.u_tracker.ui.screens.login.LoginScreen
import com.code_of_duty.u_tracker.ui.screens.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthNavItems.Login.route
    ) {
        composable(route = AuthNavItems.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthNavItems.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = AuthNavItems.Forgot.route) {
            GetVerificationTokenScreen(
                onContinue = { navController.navigate(AuthNavItems.Login.route) }
            )
        }
    }
}

