package com.atul.apodretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApodViewModel : ViewModel() {
    var apodItems by mutableStateOf<List<APODapiItem>?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var hasError by mutableStateOf(false)
        private set

    init {
        fetchApod()
    }

    private fun fetchApod() {
        viewModelScope.launch {
            isLoading = true
            hasError = false
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val today = LocalDate.now()
                val endDate = today
                val startDate = endDate.minusDays(9)

                val response = RetrofitInstance.api.getApod("UmrDcezv256EGi2G3FcpJxfNF5k2enNzEvUMQYEA", startDate.format(formatter), endDate.format(formatter))

//                Log.d("ApodViewModel", "Requesting from ${startDate.format(formatter)} to ${endDate.format(formatter)}")
//                Log.d("ApodViewModel", "Error fetching APOD list xxxxxxxxxxxxxxxxxxx : $response  ${startDate.format(formatter)}  ${endDate.format(formatter)}")

                // NASA's API returns the list in ascending date order — reverse it if needed
                apodItems = response.reversed()
                isLoading = false
            } catch (e: Exception) {
                hasError = true
                isLoading = false
                e.printStackTrace()
                Log.e("ApodViewModel", "Error fetching APOD list: ${e.localizedMessage}")
            }
        }
    }
}
