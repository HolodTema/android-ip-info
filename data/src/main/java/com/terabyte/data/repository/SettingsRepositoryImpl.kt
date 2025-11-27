package com.terabyte.data.repository

import android.content.Context
import com.terabyte.data.local.settings.SettingsDataStore
import com.terabyte.data.local.settings.SettingsUIThemeModel
import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.SettingsRepository

class SettingsRepositoryImpl(context: Context) : SettingsRepository {
    private val settingsStorage = SettingsDataStore(context)

    override suspend fun saveUITheme(theme: UITheme) {
        settingsStorage.saveUITheme(mapToSettingsUITheme(theme))
    }

    override suspend fun getUITheme(): UITheme {
        val theme = settingsStorage.getUITheme()
        return mapToUITheme(theme)
    }

    private fun mapToSettingsUITheme(theme: UITheme): SettingsUIThemeModel {
        return SettingsUIThemeModel(theme.isDarkTheme)
    }

    private fun mapToUITheme(theme: SettingsUIThemeModel): UITheme {
        return UITheme(theme.isDarkTheme)
    }
}