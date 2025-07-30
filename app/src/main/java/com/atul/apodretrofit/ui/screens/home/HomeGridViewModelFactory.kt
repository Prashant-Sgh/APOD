package com.atul.apodretrofit.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atul.apodretrofit.model.DataStoreManager

class HomeGridViewModelFactory(
    private val dataStoreManager: DataStoreManager,
    private val context: Context
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create (modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeGridViewModel::class.java)){
            return HomeGridViewModel(dataStoreManager, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}