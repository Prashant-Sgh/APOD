package com.atul.apodretrofit.ui.screens.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.data.offline.SavedItemDatabase
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.data.offline.SavedItemRepository
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.DataStoreManager
import com.atul.apodretrofit.model.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeGridViewModel(
    private val dataStoreManager: DataStoreManager,
    context: Context
) : ViewModel() {

    val database = SavedItemDatabase.getInstance(context)
    val repository = SavedItemRepository(database.savedItemDao(), context)

    private val _isDark = MutableStateFlow(false)
    val isDark: StateFlow<Boolean> = _isDark

    val _containAllSavedItems = repository.getAllSavedItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), emptyList())
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
    fun deleteSingleItem(date: String) {
        viewModelScope.launch {
            repository.deleteItemByDate(date)
        }
    }

    private val _uiState = mutableStateOf(HomeGridUiState())
    val uiState: State<HomeGridUiState> = _uiState

    private val _selectedItem = MutableStateFlow<APODapiItem?>(null)
    val selectedItem: StateFlow<APODapiItem?> = _selectedItem

    private val _offlineSelectedItem = MutableStateFlow<SavedItemEntity?>(null)
    val offlineSelectedItem: StateFlow<SavedItemEntity?> = _offlineSelectedItem

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

//    val isSelectedItemSaved : StateFlow<Boolean> = combine(
//        selectedItem, containAllSavedItems
//    ) {
//            selected, savedItems -> selected != null && savedItems.any {it.date == selected.date}
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), true)

    private val _savedItems = MutableStateFlow<Set<String>>(emptySet())
    val savedItems: StateFlow<Set<String>> = _savedItems

    fun checkItemSaved(date: String) {
        viewModelScope.launch {
            val saved = repository.isItemSaved(date)
            _isSaved.value = saved
            _savedItems.update { current ->
                if (saved) current + date else current - date
            }
        }
    }


    fun toggleSavedItem(item: SavedItemEntity) {
        viewModelScope.launch {
            if (_containAllSavedItems.value.toMutableList().any {it.date == item.date}) {
                repository.deleteItemByDate(item.date)
                checkItemSaved(item.date)
            }
            else {
                repository.insertSingleItem(item)
                checkItemSaved(item.date)
            }
        }
    }

    fun selectItem(item: APODapiItem) {
        _selectedItem.value = item
    }

    fun offlineSelectedItem(item: SavedItemEntity) {
        _offlineSelectedItem.value = item
    }

    val emptyEntity = SavedItemEntity("", "", "", "", "", "")

    fun getSaveEntity(item: APODapiItem): SavedItemEntity {
        return SavedItemEntity(item.date, item.explanation, item.hdurl, item.media_type, item.title, item.url)
    }

//    val pastDays = mutableLongStateOf(10)

    var pastDays by mutableStateOf(7L)
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
//                _uiState.value.isLoading = false
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val today = LocalDate.now()
                val endDate = today.minusDays(1)
                val startDate = endDate.minusDays(pastDays)

                val response = RetrofitInstance.api.getApod( startDate = startDate.format(formatter), endDate =  endDate.format(formatter))

                val newItems = response.reversed()

//                val combinedItems = _uiState.value.items + newItems
//                _uiState.value.items.addAll(combinedItems)

                val updatedItems = (_uiState.value.items + newItems)
                    .filter { it.url != null && it.url?.contains(".png") == false }
                    .distinctBy { it.date } // removes duplicates by date
                _uiState.value.items.clear()
                _uiState.value.items.addAll(updatedItems)

//                _uiState.value.error = null

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )

            }
            catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.toString())
            }

        }
    }
}
