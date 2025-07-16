package com.atul.apodretrofit.model

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String, // Format: "YYYY-MM-DD"
        @Query("start_date") startDate: String, // Format: "YYYY-MM-DD"
    @Query("end_date") endDate: String
    ): List<APODapiItem>
}
