package com.readychatai.dev.presentation.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddChatDialog(
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit
) {
    var chatTitle by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            androidx.compose.material3.TextButton(
                onClick = {
                    if (chatTitle.isNotBlank()) {
                        onAddChat(chatTitle.trim())
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("New Chat") },
        text = {
            androidx.compose.material3.OutlinedTextField(
                value = chatTitle,
                onValueChange = { chatTitle = it },
                label = { Text("Chat Name") },
                singleLine = true
            )
        }
    )
}
