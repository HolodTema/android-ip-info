package com.terabyte.domain.usecase

import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.SettingsRepository

class SaveUIThemeUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun execute(theme: UITheme) {
        settingsRepository.saveUITheme(theme)
    }

}