package com.readychatai.dev.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)