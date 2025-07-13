package com.atul.apodretrofit.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.model.GridItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeGridViewModel : ViewModel() {

    private val _uiState = mutableStateOf(HomeGridUiState())
    val uiState: State<HomeGridUiState> = _uiState

    init {
        loadGridItems()
    }

    private fun loadGridItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(1000) // Simulate loading

            val items = List(20) { GridItem(it, "Item $it") }

            _uiState.value = _uiState.value.copy(
                items = items,
                isLoading = false
            )
        }
    }
}
