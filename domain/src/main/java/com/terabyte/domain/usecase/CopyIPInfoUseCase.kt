package com.terabyte.domain.usecase

import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.repository.ClipboardRepository

class CopyIPInfoUseCase(private val clipboardRepository: ClipboardRepository) {

    fun execute(text: String) {
        clipboardRepository.copyToClipboard(text)
    }

}