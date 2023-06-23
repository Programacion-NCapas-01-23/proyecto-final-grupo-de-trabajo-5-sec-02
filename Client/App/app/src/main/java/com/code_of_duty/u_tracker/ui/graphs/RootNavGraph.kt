package com.code_of_duty.u_tracker.ui.graphs

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.code_of_duty.u_tracker.ui.components.BottomBar
import com.code_of_duty.u_tracker.ui.components.TopAppBar

//@Composable
//fun RootNavigationGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        route = Graph.ROOT,
//        startDestination = Graph.AUTHENTICATION
//    ) {
//        authNavGraph(navController = navController) // Llama al authNavGraph
//        composable(route = Graph.HOME) {
//            MainScreen()
//        }
//    }
//}


//@Composable
//fun RootNavigatonGraph(navController: NavHostController) {
//    Scaffold(
//        topBar = {
//            val actualDestination = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
//
//            val currentBackStackEntry = navController.currentBackStackEntry
//            val currentGraph = currentBackStackEntry?.destination?.parent?.route.toString()
//
//            when(currentGraph) {
//                Graph.AUTHENTICATION -> {}
//                Graph.HOME -> { TopBar(navController = navController) }
//                else -> { TopBarSecondary(title = actualDestination) }
//            }
//        },
//        bottomBar = { BottomBar(navController = navController) }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.secondary)
//                .padding(innerPadding)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            NavHost(
//                navController = navController,
//                route = Graph.ROOT,
//                startDestination = Graph.AUTHENTICATION
//            ) {
//                //Navegacion Autenticacion
//                authNavGraph(navController = navController)
//
//                //Navegacion pantallas principales
//                homeNavGraph(navController = navController)
//
//                //Navegacion pantallas others
//                othersNavGraph(navController = navController)
//            }
//        }
//    }
//}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val actualDestination = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
    val currentBackStackEntry = navController.currentBackStackEntry
    val currentGraph = currentBackStackEntry?.destination?.parent?.route.toString()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentGraph == Graph.AUTHENTICATION) {

            } else {
                TopAppBar(navController = navController, scrollBehavior = scrollBehavior, destination = actualDestination)
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHost(
                navController = navController,
                route = Graph.ROOT,
                startDestination = Graph.AUTHENTICATION
            ) {
                //Navegacion Autenticacion
                authNavGraph(navController = navController)

                //Navegacion pantallas principales
                homeNavGraph(navController = navController)
            }
        }
    }
}


object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}