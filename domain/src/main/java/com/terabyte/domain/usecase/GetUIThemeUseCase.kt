package com.terabyte.domain.usecase

import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.SettingsRepository

class GetUIThemeUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun execute(): UITheme {
        return settingsRepository.getUITheme()
    }

}