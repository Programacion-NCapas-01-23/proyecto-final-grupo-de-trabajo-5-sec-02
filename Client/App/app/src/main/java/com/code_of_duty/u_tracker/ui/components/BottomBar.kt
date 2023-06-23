package com.code_of_duty.u_tracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.code_of_duty.u_tracker.ui.models.NavItems
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf (
        NavItems.Pensum,
        NavItems.Assesment,
        NavItems.Term,
        NavItems.CUM,
        NavItems.Schedule,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any {it.route == currentDestination?.route} || currentDestination?.route == NavItems.Profile.route
    if (bottomBarDestination) {
        NavigationBar() {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController)
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: NavItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    UTrackerTheme() {
        NavigationBarItem(
            label = {
                Text(
                    text = screen.name,
                    style = TextStyle(fontSize = Typography.labelSmall.fontSize)
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = screen.icon),
                    contentDescription = "Navigation Icon"
                )
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true,
            onClick = {
                navController.navigate(screen.route){
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            alwaysShowLabel = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        BottomBar(navController = rememberNavController())
    }
}