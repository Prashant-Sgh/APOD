package com.atul.apodretrofit.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.GridItem
import com.atul.apodretrofit.model.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeGridViewModel : ViewModel() {

    private val _uiState = mutableStateOf(HomeGridUiState())
    val uiState: State<HomeGridUiState> = _uiState

    private val _selectedItem = MutableStateFlow<APODapiItem?>(null)
    val selectedItem: StateFlow<APODapiItem?> = _selectedItem

    fun selectItem(item: APODapiItem) {
        _selectedItem.value = item
    }

    init {
        loadGridItems()
    }

    private fun loadGridItems() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val today = LocalDate.now()
                val endDate = today.minusDays(1)
                val startDate = endDate.minusDays(9)

                val response = RetrofitInstance.api.getApod("***REMOVED***", startDate.format(formatter), endDate.format(formatter))

                _uiState.value = _uiState.value.copy(isLoading = false, items = response, error = null)

            }
            catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.toString())
            }

        }
    }
}
