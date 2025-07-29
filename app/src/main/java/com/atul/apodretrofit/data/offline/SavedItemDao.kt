package com.atul.apodretrofit.data.offline

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedItemDao {
    @Query("SELECT * FROM saved_items")
    fun getAllSavedItems(): Flow<List<SavedItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleItem (item: SavedItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheseItems(items: List<SavedItemEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_items WHERE date = :date)")
    suspend fun isItemSaved(date: String): Boolean

    @Delete
    suspend fun deleteSingleSavedItem(item: SavedItemEntity)

    @Query("DELETE FROM saved_items WHERE date = :date")
    suspend fun deleteSavedItemByDate(date: String)

    @Query("DELETE FROM saved_items")
    suspend fun deleteAllSavedItems()
}