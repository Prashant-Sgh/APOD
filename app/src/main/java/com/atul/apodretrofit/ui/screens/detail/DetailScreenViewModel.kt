package com.atul.apodretrofit.ui.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.atul.apodretrofit.model.APODapiItem

class DetailScreenViewModel: ViewModel() {
    private val _selectedItem = mutableStateOf<APODapiItem?>(null)
    val selectedItem: State<APODapiItem?> = _selectedItem

    // Call this to update the selected item
    fun selectItem(item: APODapiItem) {
        _selectedItem.value = item
    }
}