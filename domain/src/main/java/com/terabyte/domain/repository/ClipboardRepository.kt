package com.terabyte.domain.repository

interface ClipboardRepository {

    fun copyToClipboard(text: String)

}