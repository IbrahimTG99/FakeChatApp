package com.readychatai.dev.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = onDismiss,
    title: String,
    message: String,
    confirmText: String = "OK",
    dismissText: String? = null // set null if no dismiss button
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                dismissText?.let {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        )
    }
}