package com.atul.apodretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.atul.apodretrofit.data.offline.SavedItemDatabase
import com.atul.apodretrofit.data.offline.SavedItemRepository
import com.atul.apodretrofit.ui.screens.detail.DetailScreen
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import com.atul.apodretrofit.ui.screens.home.HomeScreen
import com.atul.apodretrofit.ui.screens.offlines.OfflineDetailScreen
import com.atul.apodretrofit.ui.screens.offlines.SaveItemViewModelFactory
import com.atul.apodretrofit.ui.screens.offlines.SavedItemViewModel
import com.atul.apodretrofit.ui.screens.offlines.SavedItemsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: HomeGridViewModel
) {
//    val viewModel: HomeGridViewModel = viewModel()

//    val context = LocalContext.current
//    val database = SavedItemDatabase.getInstance(context)
//    val repository = SavedItemRepository(database.savedItemDao(), context)
//    val savedItemFactory = SaveItemViewModelFactory(repository)
//    val savedItemViewModel: SavedItemViewModel = viewModel(factory = savedItemFactory)

//    val uiState = viewModel.uiState
//    val onToggleSaved = { viewModel.toggleSavedItem(it) }
//    val onItemClick = { viewModel.selectItem(it) }
//    val isDarkTheme = viewModel.isDark
//    val onThemeToggle = viewModel.toggleTheme(context)

    NavHost(navController, startDestination = NavRoutes.Home) {
        composable(NavRoutes.Home) {
//            HomeGridScreen(navController, viewModel, paddingValues = PaddingValues(0.dp))
            HomeScreen(
                uiState = viewModel.uiState,
                onToggleSaved = { viewModel.toggleSavedItem(it) },
                onItemClick = {
                    viewModel.selectItem(it)
                    navController.navigate(NavRoutes.Detail)
                              },
                isDarkTheme = viewModel.isDark.collectAsState(),
                onShowMore = {
                    viewModel.updatePastDays(viewModel.pastDays + 4)
                    viewModel.loadGridItems(viewModel.pastDays)
                }
            )
        }
        composable(NavRoutes.Detail){
            DetailScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(NavRoutes.Offline){
            SavedItemsScreen(viewModel, navController)
        }
        composable(NavRoutes.OfflineDetail){
            OfflineDetailScreen(viewModel, onBack = { navController.popBackStack() })
        }
    }
}
