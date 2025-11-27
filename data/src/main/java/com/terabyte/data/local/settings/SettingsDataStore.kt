package com.terabyte.data.local.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.terabyte.data.local.settings.DataStoreKeys.KEY_IS_DARK_THEME
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) : SettingsStorage {

    override suspend fun getUITheme(): SettingsUIThemeModel {
        val isDarkTheme = dataStore.data.map { preferences ->
            preferences[KEY_IS_DARK_THEME]
        }.firstOrNull() ?: false

        return SettingsUIThemeModel(isDarkTheme)
    }

    override suspend fun saveUITheme(theme: SettingsUIThemeModel) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_DARK_THEME] = theme.isDarkTheme
        }
    }

}

object DataStoreKeys {
    val KEY_IS_DARK_THEME = booleanPreferencesKey("keyIsDarkTheme")
}