package com.readychatai.dev.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.ChatCategoryCrossRef

data class ChatWithCategories(
    @Embedded val chat: Chat,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ChatCategoryCrossRef::class,
            parentColumn = "chatId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<Category>
)