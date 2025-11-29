package com.terabyte.ipinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.IPInfoRepository
import com.terabyte.domain.repository.SettingsRepository
import com.terabyte.domain.usecase.GetIPInfoHistoryUseCase
import com.terabyte.domain.usecase.GetIPInfoUseCase
import com.terabyte.domain.usecase.GetUIThemeUseCase
import com.terabyte.domain.usecase.SaveUIThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    ipInfoRepository: IPInfoRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val getUIThemeUseCase = GetUIThemeUseCase(settingsRepository)
    private val saveUIThemeUseCase = SaveUIThemeUseCase(settingsRepository)
    private val getIPInfoHistoryUseCase = GetIPInfoHistoryUseCase(ipInfoRepository)
    private val getIPInfoUseCase = GetIPInfoUseCase(ipInfoRepository)


    private val _stateFlowIsDarkTheme = MutableStateFlow(false)
    val stateFlowIsDarkTheme = _stateFlowIsDarkTheme.asStateFlow()


    private val _stateFlowSearchHistory = MutableStateFlow<List<IPInfo>>(emptyList())
    val stateFlowSearchHistory = _stateFlowSearchHistory.asStateFlow()

    private val _stateFlowIpInfo = MutableStateFlow<IPInfo?>(null)
    val stateFlowIpInfo = _stateFlowIpInfo.asStateFlow()


    init {
        getDarkTheme()
        loadSearchHistory()
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


    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = getIPInfoHistoryUseCase.execute()
            withContext(Dispatchers.Main) {
                _stateFlowSearchHistory.value = history
            }
        }
    }

    fun getIpInfo(ip: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val ipInfo = getIPInfoUseCase.execute(ip)

            ipInfo?.let {
                _stateFlowSearchHistory.value = listOf(ipInfo).plus(stateFlowSearchHistory.value)
            }

            withContext(Dispatchers.Main) {
                _stateFlowIpInfo.value = ipInfo
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