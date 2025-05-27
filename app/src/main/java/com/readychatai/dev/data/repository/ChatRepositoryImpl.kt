package com.readychatai.dev.data.repository

import com.readychatai.dev.data.room.dao.CategoryDao
import com.readychatai.dev.data.room.dao.ChatCategoryCrossRefDao
import com.readychatai.dev.data.room.dao.ChatDao
import com.readychatai.dev.data.room.dao.MessageDao
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.ChatCategoryCrossRef
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithCategories
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import com.readychatai.dev.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao,
    private val categoryDao: CategoryDao,
    private val crossRefDao: ChatCategoryCrossRefDao
) : ChatRepository {

    override suspend fun insertChat(chat: Chat): Long {
        return chatDao.insert(chat)
    }

    override suspend fun insertMessage(message: Message): Long {
        val messageId = messageDao.insert(message)

        // Auto-categorize based on new message content
        val categories = categoryDao.getAllCategories()
        for (category in categories) {
            if (category.keywords.any { keyword -> message.content.contains(keyword, ignoreCase = true) }) {
                val alreadyTagged = crossRefDao.get(message.chatId, category.id)
                if (alreadyTagged == null) {
                    crossRefDao.insert(ChatCategoryCrossRef(message.chatId, category.id))
                }
            }
        }

        return messageId
    }

    override suspend fun insertCategory(category: Category): Long {
        val categoryId = categoryDao.insert(category)

        // After inserting new category, scan all chats to categorize
        val allChats = chatDao.getAllChats()

        for (chat in allChats) {
            val messages = messageDao.getMessagesForChat(chat.id)
            if (messages.any { msg -> category.keywords.any { keyword -> msg.content.contains(keyword, ignoreCase = true) } }) {
                val alreadyTagged = crossRefDao.get(chat.id, categoryId.toInt())
                if (alreadyTagged == null) {
                    crossRefDao.insert(ChatCategoryCrossRef(chat.id, categoryId.toInt()))
                }
            }
        }

        return categoryId
    }

    override suspend fun getAllChats(): List<ChatWithMessagesAndCategories> {
        return chatDao.getAllChats().map {
            chatDao.getChatWithMessages(it.id)
        }
    }

    override suspend fun getChatWithCategories(chatId: Int): ChatWithCategories {
        return chatDao.getChatWithCategories(chatId)
    }

    override suspend fun getMessagesForChat(chatId: Int): List<Message> {
        return messageDao.getMessagesForChat(chatId)
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }
}
