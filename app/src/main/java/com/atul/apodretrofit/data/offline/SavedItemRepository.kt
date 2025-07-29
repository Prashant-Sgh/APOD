package com.atul.apodretrofit.data.offline

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


// In simple words this class let us use our DAO interface (SavedItemDao) safely.
class SavedItemRepository(private val ourDaoLetUs: SavedItemDao) {
    fun getAllSavedItems(): Flow<List<SavedItemEntity>> = ourDaoLetUs.getAllSavedItems()

    suspend fun insertSingleItem(item: SavedItemEntity) = ourDaoLetUs.insertSingleItem(item)

    suspend fun isItemSaved(date: String): Boolean = ourDaoLetUs.isItemSaved(date)

    suspend fun insertTheseItems(items: List<SavedItemEntity>) = ourDaoLetUs.insertTheseItems(items)

    suspend fun deleteSingleItem(item: SavedItemEntity) = ourDaoLetUs.deleteSingleSavedItem(item)

    suspend fun deleteItemByDate(date: String) = ourDaoLetUs.deleteSavedItemByDate(date)

    suspend fun deleteAllSavedItems() = ourDaoLetUs.deleteAllSavedItems()
}