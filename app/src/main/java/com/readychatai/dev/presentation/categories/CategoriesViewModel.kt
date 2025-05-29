package com.readychatai.dev.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.domain.repository.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: ChatRepository
) : ViewModel() {
    val categories: StateFlow<List<Category>> = repository.getAllCategoriesFlow()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), emptyList())


    fun addOrUpdateCategory(category: Category, isUpdate: Boolean) = viewModelScope.launch {
        repository.insertOrUpdateCategory(category, isUpdate)
    }
}