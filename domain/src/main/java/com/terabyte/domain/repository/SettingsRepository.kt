package com.terabyte.domain.repository

import com.terabyte.domain.model.UITheme

interface SettingsRepository {

    suspend fun saveUITheme(theme: UITheme)

    suspend fun getUITheme(): UITheme

}