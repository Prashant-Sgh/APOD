package com.atul.apodretrofit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atul.apodretrofit.data.offline.SavedItemDatabase
import com.atul.apodretrofit.data.offline.SavedItemRepository
import com.atul.apodretrofit.model.DataStoreManager
import com.atul.apodretrofit.ui.screens.detail.DetailScreen
import com.atul.apodretrofit.ui.screens.detail.DetailScreenViewModel
import com.atul.apodretrofit.ui.screens.home.HomeGridScreen
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import com.atul.apodretrofit.ui.screens.offlines.SaveItemViewModelFactory
import com.atul.apodretrofit.ui.screens.offlines.SavedItemViewModel
import com.atul.apodretrofit.ui.screens.offlines.SavedItemsScreen

@Composable
fun AppNavHost(
//    navController: NavHostController = rememberNavController(),
    navController: NavHostController,
    viewModel: HomeGridViewModel
) {
//    val viewModel: HomeGridViewModel = viewModel()

    val context = LocalContext.current
    val database = SavedItemDatabase.getInstance(context)
    val repository = SavedItemRepository(database.savedItemDao())
    val savedItemFactory = SaveItemViewModelFactory(repository)
    val savedItemViewModel: SavedItemViewModel = viewModel(factory = savedItemFactory)

    NavHost(navController, startDestination = NavRoutes.Home) {
        composable(NavRoutes.Home) {
            HomeGridScreen(navController, viewModel, paddingValues = PaddingValues(0.dp))
        }
        composable(NavRoutes.Detail){
            DetailScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(NavRoutes.Offline){
            SavedItemsScreen(viewModel, navController)
        }
    }
}
