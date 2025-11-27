package com.terabyte.data.local.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.terabyte.data.local.settings.DataStoreKeys.DATA_STORE_NAME
import com.terabyte.data.local.settings.DataStoreKeys.KEY_IS_DARK_THEME
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

class SettingsDataStore(private val context: Context) : SettingsStorage {

    override suspend fun getUITheme(): SettingsUIThemeModel {
        val isDarkTheme = context.dataStore.data.map { preferences ->
            preferences[KEY_IS_DARK_THEME]
        }.firstOrNull() ?: false

        return SettingsUIThemeModel(isDarkTheme)
    }

    override suspend fun saveUITheme(theme: SettingsUIThemeModel) {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_DARK_THEME] = theme.isDarkTheme
        }
    }

}

object DataStoreKeys {
    const val DATA_STORE_NAME = "settingsDataStore"
    val KEY_IS_DARK_THEME = booleanPreferencesKey("keyIsDarkTheme")
}