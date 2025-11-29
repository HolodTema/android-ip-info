package com.terabyte.domain.usecase

import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.repository.IPInfoRepository

class GetIPInfoUseCase(private val ipInfoRepository: IPInfoRepository) {

    suspend fun execute(ip: String): IPInfo? {
        return ipInfoRepository.requestIpInfo(ip)
    }

}