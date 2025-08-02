package com.atul.apodretrofit.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.atul.apodretrofit.navigation.NavRoutes
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BottomBar(isDarkTheme: StateFlow<Boolean>, navController: NavController) {

    val currentDestinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isDark = isDarkTheme.collectAsState().value

    BottomAppBar(
        containerColor = if (isDark) Color(0xFF0F172A) else Color(0xFFDCDCDC),
        contentColor = if(isDark) Color.White else Color(0xFFDCDCDC),
        actions = {
            Spacer(modifier = Modifier.weight(0.2f))
            IconButton(onClick = {
                if (currentDestinationRoute != NavRoutes.Home) {
                    navController.navigate(NavRoutes.Home) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
            Spacer(modifier = Modifier.weight(0.6f))
            IconButton(onClick = {
                if (currentDestinationRoute != NavRoutes.Offline) {
                    navController.navigate(NavRoutes.Offline) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }) {
                Icon(Icons.Default.Star, contentDescription = "Favorites")
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }
    )
}





//@Composable
//fun BottomBar(
//    navController: NavController
//    ) {
//
//    val items = listOf(
//        BottomNavItem(NavRoutes.Home, Icons.Default.Home, "Home"),
//        BottomNavItem(NavRoutes.Offline, Icons.Default.Star, "Star")
//    )
//
//    val currentDestinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//
//    NavigationBar {
//        items.forEach{
//            item ->
//            NavigationBarItem(
//                icon = { Icon(item.icon, contentDescription = item.label) },
//                selected = currentDestinationRoute == item.route,
//                onClick = {
//                    if(currentDestinationRoute != item.route){
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationId){
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                },
//                alwaysShowLabel = true
//            )
//        }
//    }
//
//}
//
//data class BottomNavItem(
//        val route: String,
//        val icon: ImageVector,
//        val label: String
//        )