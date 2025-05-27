package com.readychatai.dev.domain.repository

import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithCategories
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories

interface ChatRepository {

    suspend fun insertChat(chat: Chat): Long
    suspend fun insertMessage(message: Message): Long
    suspend fun insertCategory(category: Category): Long

    suspend fun getAllChats(): List<ChatWithMessagesAndCategories>
    suspend fun getChatWithCategories(chatId: Int): ChatWithCategories

    suspend fun getMessagesForChat(chatId: Int): List<Message>
    suspend fun getAllCategories(): List<Category>
}
