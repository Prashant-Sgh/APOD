package com.atul.apodretrofit.model

class APODapi : ArrayList<APODapiItem>()

data class APODapiItem(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)