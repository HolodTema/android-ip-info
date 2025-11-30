package com.terabyte.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.terabyte.domain.repository.ClipboardRepository

class ClipboardRepositoryImpl(context: Context) : ClipboardRepository {
    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    override fun copyToClipboard(text: String) {
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }

}