package com.readychatai.dev.ui.screen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.readychatai.dev.domain.model.Category
import com.readychatai.dev.domain.model.Message

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(messages) { _, message ->
            ChatCard(
                sender = message.sender,
                messagePreview = message.content,
                time = message.timestamp.toString(),
                categories = message.categories.map { it.name }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    val messages = listOf(
        Message(1, "Hello, how are you?", 162, "John Doe", listOf(Category(setOf("family"), "Personal", id = 1))),
        Message(2, "I'm doing well, thank you!", 163, "Jane Smith", emptyList()),
        Message(3, "Hello, how are you?", 162, "John Doe", listOf(Category(setOf("family"), "Personal", id = 2), Category(setOf("work"), "Work",
            id = 3))),
    )
    MessageList(messages = messages)
}
