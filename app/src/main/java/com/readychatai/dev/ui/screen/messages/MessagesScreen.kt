package com.readychatai.dev.ui.screen.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.readychatai.dev.domain.model.Category
import com.readychatai.dev.domain.model.Message
import com.readychatai.dev.ui.screen.components.CategoryFilterChipsRow
import com.readychatai.dev.ui.screen.components.MessageList

@Composable
fun MessagesScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Messages",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        CategoryFilterChipsRow(
            categories = listOf("Category 1", "Category 2", "Category 3"),
            selectedCategory = "Category 1",
            onCategorySelected = {}
        )
        val messages = listOf(
            Message(1, "Hello, how are you?", 162, "John Doe", listOf(Category(setOf("family"), "Personal", id = 1))),
            Message(2, "I'm doing well, thank you!", 163, "Jane Smith", emptyList()),
            Message(3, "Hello, how are you?", 162, "John Doe", listOf(Category(setOf("family"), "Personal", id = 2), Category(setOf("work"), "Work", id = 3))),
        )
        MessageList(messages = messages)
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesPreview() {
    MessagesScreen()
}
