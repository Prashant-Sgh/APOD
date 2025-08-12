package com.atul.apodretrofit.data.offline

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedItemEntity::class], version = 1, exportSchema = true)
abstract class SavedItemDatabase: RoomDatabase() {
    abstract fun savedItemDao() : SavedItemDao

    companion object{
        @Volatile private var INSTANCE: SavedItemDatabase? = null

        fun getInstance(context: Context): SavedItemDatabase {
            return INSTANCE?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SavedItemDatabase::class.java,
                    "saved_items_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}