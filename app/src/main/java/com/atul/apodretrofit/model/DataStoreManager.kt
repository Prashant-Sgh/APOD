package com.atul.apodretrofit.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme")

class DataStoreManager(private val context: Context) {
    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }


    suspend fun setDarkMode(context: Context, enable: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enable
        }
    }

    val darkModeFlow: Flow<Boolean> = context.themeDataStore.data.map  { preferences -> preferences[DARK_MODE_KEY] ?: false }

}