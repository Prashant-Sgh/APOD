package com.atul.apodretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.ui.screens.detail.DetailScreen
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import com.atul.apodretrofit.ui.screens.home.HomeScreen
import com.atul.apodretrofit.ui.screens.offlines.OfflineDetailScreen
import com.atul.apodretrofit.ui.screens.offlines.SavedItemsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: HomeGridViewModel
) {

    val selectedItem by viewModel.selectedItem.collectAsState()
    val selectedItemSavedItemEntity = SavedItemEntity(date = selectedItem?.date ?: "", explanation = selectedItem?.explanation ?: "", hdurl = selectedItem?.hdurl ?: "", media_type = selectedItem?.media_type ?: "", url = selectedItem?.url ?: "", title = selectedItem?.title ?: "")
    val isSaved by viewModel.isSaved.collectAsState()
    val isDark = viewModel.isDark.collectAsState()

    NavHost(navController, startDestination = NavRoutes.Home) {
        composable(NavRoutes.Home) {
            HomeScreen(
                viewModel = viewModel,
                uiState = viewModel.uiState,
                onToggleSaved = {
                    viewModel.toggleSavedItem(it)
//                    viewModel.checkItemSaved(it.date)
                                },
                onItemClick = {
                    viewModel.selectItem(it)
                    navController.navigate(NavRoutes.Detail)
                              },
                isDarkTheme = isDark,
                onShowMore = {
                    viewModel.updatePastDays(viewModel.pastDays + 9)
                    viewModel.loadGridItems(viewModel.pastDays)
                }
            )
        }
        composable(NavRoutes.Detail){
            LaunchedEffect(selectedItem?.date) {
                viewModel.checkItemSaved(selectedItem?.date ?: "")
            }
            DetailScreen(
                viewModel = viewModel,
                selectedItem = selectedItemSavedItemEntity,
                isItemSaved = isSaved,
                toggleSavedItem = viewModel::toggleSavedItem,
                onBack = {
                    navController.popBackStack()
                },
                isDarkTheme = isDark.value,
//                checkIfSaved = {date -> viewModel.checkItemSaved(date) }
            )
        }
        composable(NavRoutes.Offline){
            SavedItemsScreen(viewModel, navController)
        }
        composable(NavRoutes.OfflineDetail){
            OfflineDetailScreen(
                viewModel,
                onBack = {
                    navController.popBackStack()
                },
                isDarkTheme = isDark.value
            )
        }
    }
}
