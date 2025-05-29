package com.readychatai.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import com.readychatai.dev.domain.repository.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatAppSharedViewModel(
    private val repository: ChatRepository
) : ViewModel() {
    val chats: StateFlow<List<ChatWithMessagesAndCategories>> = repository.getAllChatsFlow()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), emptyList())

    val categories: StateFlow<List<Category>> = repository.getAllCategoriesFlow()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), emptyList())

    fun addChat(chat: Chat) = viewModelScope.launch {
        repository.insertChat(chat)
    }

    fun addMessage(chatId: Int, content: String) {
        viewModelScope.launch {
            val message = Message(
                chatId = chatId,
                content = content,
                timestamp = System.currentTimeMillis(),
                sender = "You" // will be changed in future
            )
            repository.insertMessage(message)
        }
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    fun getMessagesForChat(chatId: Int): StateFlow<List<Message>> {
        return repository.getMessagesForChatFlow(chatId)
            .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), emptyList())
    }
}