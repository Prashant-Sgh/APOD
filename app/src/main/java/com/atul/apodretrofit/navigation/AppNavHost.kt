package com.atul.apodretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atul.apodretrofit.ui.screens.home.HomeGridScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeGridScreen(onItemClick = { item ->
                // Handle navigation or action
                println("Clicked on: ${item.title}")
            })
        }
        composable("gridItem"){

    }
    }
}
