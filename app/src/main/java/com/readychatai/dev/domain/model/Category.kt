package com.readychatai.dev.domain.model

data class Category(
    val keywords: Set<String>,
    val name: String,
    val id: Int
)
