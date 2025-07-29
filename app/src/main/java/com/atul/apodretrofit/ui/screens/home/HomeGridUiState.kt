package com.atul.apodretrofit.ui.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.GridItem

data class HomeGridUiState(
    var isLoading: Boolean = false,
//    val items: List<APODapiItem> = emptyList(),
    var items: SnapshotStateList<APODapiItem> = mutableStateListOf(),
    var error: String? = null
)
