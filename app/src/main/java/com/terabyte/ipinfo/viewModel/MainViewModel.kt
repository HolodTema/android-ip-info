package com.terabyte.ipinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.IPInfoRepository
import com.terabyte.domain.repository.SettingsRepository
import com.terabyte.domain.usecase.GetUIThemeUseCase
import com.terabyte.domain.usecase.SaveUIThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val ipInfoRepository: IPInfoRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val getUIThemeUseCase = GetUIThemeUseCase(settingsRepository)
    private val saveUIThemeUseCase = SaveUIThemeUseCase(settingsRepository)


    private val _stateFlowIsDarkTheme = MutableStateFlow(false)
    val stateFlowIsDarkTheme = _stateFlowIsDarkTheme.asStateFlow()


    init {
        getDarkTheme()
    }


    fun getDarkTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDarkTheme = getUIThemeUseCase.execute().isDarkTheme
            withContext(Dispatchers.Main) {
                _stateFlowIsDarkTheme.value = isDarkTheme
            }
        }
    }


    fun saveDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val theme = UITheme(isDarkTheme)
            saveUIThemeUseCase.execute(theme)
            withContext(Dispatchers.Main) {
                _stateFlowIsDarkTheme.value = isDarkTheme
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val ipInfoRepository: IPInfoRepository,
        private val settingsRepository: SettingsRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(ipInfoRepository, settingsRepository) as T
        }
    }
}