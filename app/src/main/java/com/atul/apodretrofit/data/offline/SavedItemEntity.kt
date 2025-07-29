package com.atul.apodretrofit.data.offline

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_items")
data class SavedItemEntity(
    @PrimaryKey val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val title: String,
    val url: String
)
