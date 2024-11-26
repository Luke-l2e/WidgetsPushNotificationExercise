package edu.hhn.widgetspushnotifications.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")

object DataStoreManager {
    private val counterKey = intPreferencesKey("counter")

    fun getCounter(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[counterKey] ?: 0
        }
    }

    suspend fun modifyCounter(context: Context, addValue: Int) {
        context.dataStore.edit { preferences ->
            val currentCounterValue = preferences[counterKey] ?: 0
            preferences[counterKey] = (currentCounterValue + addValue)
        }
    }
}
