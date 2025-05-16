package com.readychatai.dev.domain.model

data class Message(
    val id: Long,
    val content: String,
    val timestamp: Long,
    val sender: String,
    val categories: List<Category>
)
