package com.atul.apodretrofit.ui.screens.offlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atul.apodretrofit.data.offline.SavedItemRepository

class SaveItemViewModelFactory(
    private val repository: SavedItemRepository
): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedItemViewModel::class.java)) {
           @Suppress("UNCHECKED_CAST")
            return SavedItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }