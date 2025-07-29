package com.atul.apodretrofit.ui.screens.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.data.offline.SavedItemDatabase
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.data.offline.SavedItemRepository
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.DataStoreManager
import com.atul.apodretrofit.model.GridItem
import com.atul.apodretrofit.model.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeGridViewModel(
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : ViewModel() {

    private val _isDark = MutableStateFlow(false)
    val isDark: StateFlow<Boolean> = _isDark

    val database = SavedItemDatabase.getInstance(context)
    val repository = SavedItemRepository(database.savedItemDao())

    val _containAllSavedItems = repository.getAllSavedItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val containAllSavedItems: StateFlow<List<SavedItemEntity>> = _containAllSavedItems

//    fun isSelectedItemSaved(item: APODapiItem?): Boolean = item != null && _containAllSavedItems.value.toMutableList().any{it.date == item.date}

    init {
        viewModelScope.launch {
            dataStoreManager.darkModeFlow
                .collect{iisDark ->
            _isDark.value = iisDark}
        }
    }

    fun toggleTheme (context: Context) {
        viewModelScope.launch {
            val currentTheme = dataStoreManager.darkModeFlow.first()
            dataStoreManager.setDarkMode(context, !currentTheme)
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            repository.deleteAllSavedItems()
        }
    }

    private val _uiState = mutableStateOf(HomeGridUiState())
    val uiState: State<HomeGridUiState> = _uiState

    private val _selectedItem = MutableStateFlow<APODapiItem?>(null)
    val selectedItem: StateFlow<APODapiItem?> = _selectedItem

    private val _offlineSelectedItem = MutableStateFlow<SavedItemEntity?>(null)
    val offlineSelectedItem: StateFlow<SavedItemEntity?> = _offlineSelectedItem


    val isSelectedItemSaved : StateFlow<Boolean> = combine(
        selectedItem, containAllSavedItems
    ) {
            selected, savedItems -> selected != null && savedItems.any {it.date == selected.date}
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun checkItemSaved(item: APODapiItem, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isSaved = repository.isItemSaved(item.date)
            onResult(isSaved)
        }
    }

    fun toggleSavedItem(APODitem: APODapiItem) {
        val item: SavedItemEntity = SavedItemEntity(APODitem.date, APODitem.explanation, APODitem.hdurl, APODitem.media_type, APODitem.title, APODitem.url)
        viewModelScope.launch {
            val isSaved = repository.isItemSaved(APODitem.date)
            if (_containAllSavedItems.value.toMutableList().any {it.date == APODitem.date}) {
                repository.deleteItemByDate(item.date)
            }
            else {
                repository.insertSingleItem(item)
            }
        }
    }

    fun selectItem(item: APODapiItem) {
        _selectedItem.value = item
    }

    fun offlineSelectedItem(item: SavedItemEntity) {
        _offlineSelectedItem.value = item
    }

//    val pastDays = mutableLongStateOf(10)

    var pastDays by mutableStateOf(9L)
        private set

    fun updatePastDays(newDays: Long) {
        pastDays = newDays
    }

    init {
        loadGridItems(pastDays)
    }

    fun loadGridItems(pastDays: Long) {
        viewModelScope.launch {
            try {
//                _uiState.value = _uiState.value.copy(isLoading = true)
                _uiState.value.isLoading = false
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val today = LocalDate.now()
                val endDate = today.minusDays(1)
                val startDate = endDate.minusDays(pastDays)

                val response = RetrofitInstance.api.getApod("***REMOVED***", startDate.format(formatter), endDate.format(formatter))

                val newItems = response.reversed()
                val combinedItems = _uiState.value.items + newItems

                _uiState.value.items.addAll(combinedItems)
                _uiState.value.error = null

            }
            catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.toString())
            }

        }
    }
}
