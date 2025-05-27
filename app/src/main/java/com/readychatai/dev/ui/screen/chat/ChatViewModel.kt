package com.readychatai.dev.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import com.readychatai.dev.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _chats = MutableStateFlow<List<ChatWithMessagesAndCategories>>(emptyList())
    val chats: StateFlow<List<ChatWithMessagesAndCategories>> = _chats.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _chatCategories = MutableStateFlow<Map<Int, List<Category>>>(emptyMap())
    val chatCategories: StateFlow<Map<Int, List<Category>>> = _chatCategories.asStateFlow()

    init {
        refreshAllData()
    }

    fun refreshAllData() {
        viewModelScope.launch {
            val allChats = repository.getAllChats()
            _chats.value = allChats

            val allCategories = repository.getAllCategories()
            _categories.value = allCategories

            val chatCategoryMap = allChats.associate { chat ->
                chat.chat.id to repository.getChatWithCategories(chat.chat.id).categories
            }
            _chatCategories.value = chatCategoryMap
        }
    }

    fun addChat(chat: Chat) = viewModelScope.launch {
        repository.insertChat(chat)
        refreshAllData()
    }

    fun addMessage(message: Message) = viewModelScope.launch {
        repository.insertMessage(message)
        refreshAllData()
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
        refreshAllData()
    }
}
