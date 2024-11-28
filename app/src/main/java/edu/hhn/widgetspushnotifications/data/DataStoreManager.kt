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

/**
 * An utility object for managing the application's preferences using DataStore.
 *
 * This object specifically handles the operations for a counter value.
 */
object DataStoreManager {
    private val counterKey = intPreferencesKey("counter")

    /**
     * Retrieves the current counter value from the DataStore as a Flow.
     *
     * The Flow emits the latest value of the counter stored in the DataStore. Returns `0` if no value exists.
     *
     * @param context the application context
     * @return a Flow emitting the current counter value
     */
    fun getCounter(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[counterKey] ?: 0
        }
    }

    /**
     * Updates the counter value in the DataStore by adding a specified value.
     *
     * This function retrieves the current counter value from the DataStore, adds the provided value
     * (`addValue`) to it, and stores the updated value back in the DataStore.
     *
     * @param context the application context
     * @param addValue the value to be added to the current counter
     */
    suspend fun modifyCounter(context: Context, addValue: Int) {
        context.dataStore.edit { preferences ->
            val currentCounterValue = preferences[counterKey] ?: 0
            preferences[counterKey] = (currentCounterValue + addValue)
        }
    }
}
