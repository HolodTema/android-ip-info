package com.terabyte.data.local.settings

interface SettingsStorage {

    suspend fun saveUITheme(theme: SettingsUIThemeModel)

    suspend fun getUITheme(): SettingsUIThemeModel
}