package com.readychatai.dev.presentation.model

import androidx.compose.ui.graphics.Color

data class ChatThread(
    val id: String,
    val senderName: String,
    val message: String,
    val time: String,
    val category: String,
    val avatarColor: Color = Color.Blue,
    val isRead: Boolean = true,
    val categoryIds: List<String>
)
