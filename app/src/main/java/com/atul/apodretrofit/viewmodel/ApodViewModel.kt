package com.atul.apodretrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class ApodViewModel : ViewModel() {
    var apodItem by mutableStateOf<APODapiItem?>(null)
        private set

    var hasError by mutableStateOf(false)
        private set

    init {
        fetchApod()
    }

    private fun fetchApod() {
        viewModelScope.launch {
            try {
                apodItem = RetrofitInstance.api.getApod("UmrDcezv256EGi2G3FcpJxfNF5k2enNzEvUMQYEA")
                hasError = false
            } catch (e: Exception) {
                hasError = true
                e.printStackTrace()
            }
        }
    }
}
