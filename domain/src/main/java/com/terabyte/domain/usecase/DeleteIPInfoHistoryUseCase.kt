package com.terabyte.domain.usecase

import com.terabyte.domain.repository.IPInfoRepository

class DeleteIPInfoHistoryUseCase(private val ipInfoRepository: IPInfoRepository) {

    suspend fun execute() {
        ipInfoRepository.deleteIpInfoHistory()
    }
}