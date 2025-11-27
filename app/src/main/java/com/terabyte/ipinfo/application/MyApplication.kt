package com.terabyte.ipinfo.application

import android.app.Application
import com.terabyte.data.repository.IPInfoRepositoryImpl
import com.terabyte.data.repository.SettingsRepositoryImpl
import com.terabyte.domain.repository.IPInfoRepository
import com.terabyte.domain.repository.SettingsRepository

class MyApplication : Application() {

    lateinit var ipInfoRepository: IPInfoRepository
    lateinit var settingsRepository: SettingsRepository


    override fun onCreate() {
        super.onCreate()
        ipInfoRepository = IPInfoRepositoryImpl(applicationContext)
        settingsRepository = SettingsRepositoryImpl(applicationContext)
    }

}