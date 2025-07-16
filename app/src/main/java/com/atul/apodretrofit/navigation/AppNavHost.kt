package com.atul.apodretrofit.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atul.apodretrofit.ui.screens.detail.DetailScreen
import com.atul.apodretrofit.ui.screens.detail.DetailScreenViewModel
import com.atul.apodretrofit.ui.screens.home.HomeGridScreen
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    val viewModel: HomeGridViewModel = viewModel()

    NavHost(navController, startDestination = NavRoutes.Home) {
        composable(NavRoutes.Home) {
            HomeGridScreen(navController, viewModel)
        }
        composable(NavRoutes.Detail){
            DetailScreen(viewModel)
        }
    }
}
