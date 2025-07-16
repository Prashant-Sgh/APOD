package com.atul.apodretrofit.ui.screens.home

import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.GridItem

data class HomeGridUiState(
    val isLoading: Boolean = false,
//    val items: List<GridItem> = emptyList(),
    val items: List<APODapiItem> = emptyList(),
    val error: String? = null
)
