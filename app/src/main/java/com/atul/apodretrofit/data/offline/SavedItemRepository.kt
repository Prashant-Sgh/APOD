package com.atul.apodretrofit.data.offline

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import java.io.File


// In simple words this class let us use our DAO interface (SavedItemDao) safely.
class SavedItemRepository(
    private val ourDaoLetUs: SavedItemDao,
    val context: Context
) {
    fun getAllSavedItems(): Flow<List<SavedItemEntity>> = ourDaoLetUs.getAllSavedItems()


    // Insert to room and change url to file name --> change this to file *path
    suspend fun insertSingleItem(item: SavedItemEntity) {
        withContext(Dispatchers.IO) {
            val imageBytes = ImageStorageManager.downloadImageBytes(item.url)
            val updatedItem = if (imageBytes != null) {
                ImageStorageManager.saveImage(context, "${item.date}.jpg", imageBytes)
                val file = File(context.filesDir, "${item.date}.jpg")
                item.copy(url = file.absolutePath)
            }
            else {
                item
            }
            ourDaoLetUs.insertSingleItem(updatedItem)
        }
    }

    suspend fun isItemSaved(date: String): Boolean = ourDaoLetUs.isItemSaved(date)

    suspend fun insertTheseItems(items: List<SavedItemEntity>) = ourDaoLetUs.insertTheseItems(items)

    suspend fun deleteSingleItem(item: SavedItemEntity) {
        ourDaoLetUs.deleteSingleSavedItem(item)

        withContext(Dispatchers.IO) {
            ImageStorageManager.deleteImage(context, "${item.date}.jpg")
        }
    }

    suspend fun deleteItemByDate(date: String) = ourDaoLetUs.deleteSavedItemByDate(date)

    suspend fun deleteAllSavedItems() {
        ourDaoLetUs.deleteAllSavedItems()

        withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.forEach { file ->
                if (file.extension == "jpg") file.delete()
            }
        }
    }
}