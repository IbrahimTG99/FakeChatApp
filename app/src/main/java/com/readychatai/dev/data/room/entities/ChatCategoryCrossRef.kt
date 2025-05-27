package com.readychatai.dev.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["chatId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = Chat::class,
            parentColumns = ["id"],
            childColumns = ["chatId"],
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],

        )
    ],
)
data class ChatCategoryCrossRef(
    val chatId: Int,
    val categoryId: Int
)
