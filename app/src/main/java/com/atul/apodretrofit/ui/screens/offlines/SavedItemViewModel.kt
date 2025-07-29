package com.atul.apodretrofit.ui.screens.offlines

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.data.offline.SavedItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedItemViewModel(
    private val fromRepository: SavedItemRepository
) : ViewModel() {
//    private val _containAllSavedItems = MutableStateFlow<List<SavedItemEntity>>(emptyList())
    private val _containAllSavedItems = fromRepository.getAllSavedItems().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())
    val containAllSavedItems: StateFlow<List<SavedItemEntity>> = _containAllSavedItems

    fun toggleSavedItem(item: SavedItemEntity) {
        viewModelScope.launch {
            val currentListOfSavedItemsInDatabase = _containAllSavedItems.value.toMutableList()
            if (currentListOfSavedItemsInDatabase.any{it.date == item.date}){
                fromRepository.deleteSingleItem(item)
            }
            else {
                fromRepository.insertSingleItem(item)
            }
        }
    }

    fun insertSingleItem(item: SavedItemEntity) {
        viewModelScope.launch {
            fromRepository.insertSingleItem(item)
        }
    }

    fun deleteItemByDate(date: String) {
        viewModelScope.launch {
            fromRepository.deleteItemByDate(date)
        }
    }

    fun deleteAllSavedItems() {
     viewModelScope.launch {
         fromRepository.deleteAllSavedItems()
     }
    }

}

//class SavedItemViewModel(
//    private val repository: SavedItemRepository
//): ViewModel() {
//
//    private val _savedItems = MutableStateFlow<List<SavedItemEntity>>(emptyList())
//    val savedItems: StateFlow<List<SavedItemEntity>> = _savedItems.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            _savedItems.value = repository.getAllSavedItems()
//        }
//    }
//
//    fun toggleSavedItem(item: SavedItemEntity){
//        viewModelScope.launch {}
//    }
//
//
//}