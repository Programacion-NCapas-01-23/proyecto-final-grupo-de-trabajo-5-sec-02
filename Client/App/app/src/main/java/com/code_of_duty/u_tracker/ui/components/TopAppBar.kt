package com.code_of_duty.u_tracker.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.code_of_duty.u_tracker.ui.models.AuthNavItems
import com.code_of_duty.u_tracker.ui.models.MainNavItems
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, scrollBehavior: TopAppBarScrollBehavior, destination: String?) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val title = getTitleFromRoute(currentDestination?.route)

    var showMenu by remember {
        mutableStateOf(false)
    }

    UTrackerTheme() {
        CenterAlignedTopAppBar(
            title = { Text(text = title,) },
            navigationIcon = {
                if (isMainGraph(currentDestination)) {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                    DropDownMenu(showMenu, { showMenu = false }, navController = navController)

                } else IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                //var checked by remember { mutableStateOf(false) }
//            IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
//                if (checked) {
//                    Icon(
//                        painter = painterResource(id = NavItems.Profile.icon ),
//                        contentDescription = "User Profile Selected"
//                    )
//                } else {
//                    Icon(
//                        painter = painterResource(id = R.drawable.outline_account_circle),
//                        contentDescription = "User Profile"
//                    )
//                }
//            }
                if(isMainGraph(currentDestination)) {
                    IconButton(onClick = {
                        navController.navigate(MainNavItems.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = MainNavItems.Profile.icon),
                            contentDescription = "User Profile"
                        )
                    }
                }
            },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
//        ),
            scrollBehavior = scrollBehavior
        )
    }
}

private fun getTitleFromRoute(route: String?): String {
    val mainNavScreens = listOf<MainNavItems>(
        MainNavItems.Pensum,
        MainNavItems.Assesment,
        MainNavItems.Term,
        MainNavItems.CUM,
        MainNavItems.Schedule,
        MainNavItems.Profile
    )

    val authNavScreens = listOf<AuthNavItems>(
        AuthNavItems.Login,
        AuthNavItems.SignUp,
        AuthNavItems.Forgot
    )

    val matchingMainNavScreen = mainNavScreens.find { it.route == route }

    if (matchingMainNavScreen != null) {
        return matchingMainNavScreen.name
    }

    val matchingAuthNavScreen = authNavScreens.find { it.route == route }

    if (matchingAuthNavScreen != null) {
        return matchingAuthNavScreen.name
    }

    return ""
}

private fun isMainGraph(destination: NavDestination?): Boolean {
    val screens = listOf (
        MainNavItems.Pensum,
        MainNavItems.Assesment,
        MainNavItems.Term,
        MainNavItems.CUM,
        MainNavItems.Schedule,
        MainNavItems.Profile
    )

    return destination?.hierarchy?.any { screen ->
        screens.any { it.route == screen.route }
    } == true
}


