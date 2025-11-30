package com.terabyte.ipinfo.viewModel

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.model.UITheme
import com.terabyte.domain.repository.ClipboardRepository
import com.terabyte.domain.repository.IPInfoRepository
import com.terabyte.domain.repository.SettingsRepository
import com.terabyte.domain.usecase.CopyIPInfoUseCase
import com.terabyte.domain.usecase.DeleteIPInfoHistoryUseCase
import com.terabyte.domain.usecase.DeleteIPInfoItemUseCase
import com.terabyte.domain.usecase.GetIPInfoHistoryUseCase
import com.terabyte.domain.usecase.GetIPInfoUseCase
import com.terabyte.domain.usecase.GetUIThemeUseCase
import com.terabyte.domain.usecase.SaveUIThemeUseCase
import com.terabyte.ipinfo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

class MainViewModel(
    ipInfoRepository: IPInfoRepository,
    settingsRepository: SettingsRepository,
    clipboardRepository: ClipboardRepository
) : ViewModel() {
    private val getUIThemeUseCase = GetUIThemeUseCase(settingsRepository)
    private val saveUIThemeUseCase = SaveUIThemeUseCase(settingsRepository)
    private val getIPInfoHistoryUseCase = GetIPInfoHistoryUseCase(ipInfoRepository)
    private val getIPInfoUseCase = GetIPInfoUseCase(ipInfoRepository)
    private val deleteIPInfoHistoryUseCase = DeleteIPInfoHistoryUseCase(ipInfoRepository)
    private val deleteIPInfoItemUseCase = DeleteIPInfoItemUseCase(ipInfoRepository)
    private val copyIPInfoUseCase = CopyIPInfoUseCase(clipboardRepository)


    private val _stateFlowIsDarkTheme = MutableStateFlow(false)
    val stateFlowIsDarkTheme = _stateFlowIsDarkTheme.asStateFlow()


    private val _stateFlowSearchHistory = MutableStateFlow<List<IPInfo>>(emptyList())
    val stateFlowSearchHistory = _stateFlowSearchHistory.asStateFlow()

    private val _stateFlowIpInfo = MutableStateFlow<IPInfo?>(null)
    val stateFlowIpInfo = _stateFlowIpInfo.asStateFlow()

    private val _stateFlowExpandedIpInfoId = MutableStateFlow<UUID?>(null)
    val stateFlowExpandedIpInfoId = _stateFlowExpandedIpInfoId.asStateFlow()


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

    fun deleteAllIpInfo() {
        _stateFlowExpandedIpInfoId.value = null
        if (stateFlowSearchHistory.value.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteIPInfoHistoryUseCase.execute()
                withContext(Dispatchers.Main) {
                    loadSearchHistory()
                }
            }
        }
    }

    fun changeExpandedItemId(uuid: UUID?) {
        _stateFlowExpandedIpInfoId.value = uuid
    }

    fun deleteIpInfoItem(ipInfo: IPInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteIPInfoItemUseCase.execute(ipInfo)
            withContext(Dispatchers.Main) {
                if (stateFlowExpandedIpInfoId.value == ipInfo.id) {
                    _stateFlowExpandedIpInfoId.value = null
                }
                loadSearchHistory()
            }
        }
    }

    fun copyIPInfoToClipboard(resources: Resources, ipInfo: IPInfo) {
        val text = getIpInfoUserText(resources, ipInfo)
        copyIPInfoUseCase.execute(text)
    }

    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = getIPInfoHistoryUseCase.execute()
            withContext(Dispatchers.Main) {
                _stateFlowSearchHistory.value = history
            }
        }
    }

    private fun getIpInfoUserText(resources: Resources, ipInfo: IPInfo): String {
        val textNoData = resources.getString(R.string.no_data)

        val textLocation = if(ipInfo.latitude == null || ipInfo.longitude == null) {
            textNoData
        }
        else {
            "${ipInfo.latitude}   ${ipInfo.longitude}"
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val textDate = dateFormat.format(ipInfo.infoDate)

        return """
            ${resources.getString(R.string.ip_address)} ${ipInfo.ip ?: textNoData}
            ${resources.getString(R.string.host)} ${ipInfo.hostname ?: textNoData}
            ${resources.getString(R.string.country)} ${ipInfo.country ?: textNoData}
            ${resources.getString(R.string.region)} ${ipInfo.region ?: textNoData}
            ${resources.getString(R.string.city)} ${ipInfo.city ?: textNoData}
            ${resources.getString(R.string.organization)} ${ipInfo.organization ?: textNoData}
            ${resources.getString(R.string.timezone)} ${ipInfo.timezone ?: textNoData}
            ${resources.getString(R.string.location)} $textLocation
            ${resources.getString(R.string.date_of_search)} $textDate
        """.trimIndent()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val ipInfoRepository: IPInfoRepository,
        private val settingsRepository: SettingsRepository,
        private val clipboardRepository: ClipboardRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(ipInfoRepository, settingsRepository, clipboardRepository) as T
        }
    }
}