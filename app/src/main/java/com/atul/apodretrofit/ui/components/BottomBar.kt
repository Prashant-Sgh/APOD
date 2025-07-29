package com.atul.apodretrofit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.savedstate.savedState
import com.atul.apodretrofit.navigation.NavRoutes

@Composable
fun BottomBar(
    navController: NavController
    ) {

    val items = listOf(
        BottomNavItem(NavRoutes.Home, Icons.Default.Home, "Home"),
        BottomNavItem(NavRoutes.Offline, Icons.Default.Star, "Star")
    )

    val currentDestinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach{
            item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                selected = currentDestinationRoute == item.route,
                onClick = {
                    if(currentDestinationRoute != item.route){
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }

}

data class BottomNavItem(
        val route: String,
        val icon: ImageVector,
        val label: String
        )