package com.readychatai.dev.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.ChatCategoryCrossRef
import com.readychatai.dev.data.room.entities.Message

data class ChatWithMessagesAndCategories(
    @Embedded val chat: Chat,

    @Relation(
        parentColumn = "id", // The primary key of Chat
        entityColumn = "chatId" // Foreign key in the Message table
    )
    val messages: List<Message>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ChatCategoryCrossRef::class, parentColumn = "chatId",entityColumn = "categoryId")
    )
    val categories: List<Category>
)
