package com.atul.apodretrofit.model

import retrofit2.http.GET
import retrofit2.http.Query
import com.atul.apodretrofit.BuildConfig

interface NasaApi {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY,
        @Query("start_date") startDate: String, // Format: "YYYY-MM-DD"
    @Query("end_date") endDate: String // Format: "YYYY-MM-DD"
    ): List<APODapiItem>
}
