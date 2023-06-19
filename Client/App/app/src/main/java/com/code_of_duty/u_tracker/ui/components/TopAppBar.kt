package com.code_of_duty.u_tracker.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.ui.graphs.Graph
import com.code_of_duty.u_tracker.ui.models.NavItems
import com.code_of_duty.u_tracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, scrollBehavior: TopAppBarScrollBehavior) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val title = getTitleFromRoute(currentDestination?.route)
    var showMenu by remember {
        mutableStateOf(false)
    }
    CenterAlignedTopAppBar(
        title = { Text(text = title,) },
        navigationIcon = {
            if(isInHomeNavGraph(currentDestination)){
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
              DropDownMenu(showMenu, {showMenu = false},navController = navController)

            } else IconButton(onClick = { navController.popBackStack() }){Icon(Icons.Filled.ArrowBack, contentDescription = "Back")}
        },
        actions = {
            var checked by remember { mutableStateOf(false) }
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
            IconButton(onClick = {
                navController.navigate(NavItems.Profile.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
                }
            }) {
                Icon(
                    painter = painterResource(id = NavItems.Profile.icon),
                    contentDescription = "User Profile"
                )
            }
        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
//        ),
        scrollBehavior = scrollBehavior
    )
}

private fun getTitleFromRoute(route: String?): String {

    val screens = listOf (
        NavItems.Pensum,
        NavItems.Assesment,
        NavItems.Term,
        NavItems.CUM,
        NavItems.Schedule,
        NavItems.Profile
    )

    val matchingScreen = screens.find { it.route == route }
    return matchingScreen?.name ?: ""
}

private fun isInHomeNavGraph(destination: NavDestination?): Boolean {
    val screens = listOf (
        NavItems.Pensum,
        NavItems.Assesment,
        NavItems.Term,
        NavItems.CUM,
        NavItems.Schedule,
        NavItems.Profile

    )

    return destination?.hierarchy?.any { screen ->
        screens.any { it.route == screen.route }
    } == true
}


