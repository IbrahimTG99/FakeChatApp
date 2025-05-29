package com.readychatai.dev.domain.repository

import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithCategories
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun insertChat(chat: Chat): Long
    suspend fun insertMessage(message: Message): Long
    suspend fun insertOrUpdateCategory(category: Category, isUpdate: Boolean = false): Long

    suspend fun getAllChats(): List<ChatWithMessagesAndCategories>
    fun getAllChatsFlow(): Flow<List<ChatWithMessagesAndCategories>>
    suspend fun getChatWithCategories(chatId: Int): ChatWithCategories
    fun getMessagesForChatFlow(chatId: Int): Flow<List<Message>>
    suspend fun getAllCategories(): List<Category>
    fun getAllCategoriesFlow(): Flow<List<Category>>
}
