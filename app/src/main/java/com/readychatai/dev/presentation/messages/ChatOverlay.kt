package com.readychatai.dev.presentation.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.readychatai.dev.presentation.ChatAppSharedViewModel

@Composable
fun ChatOverlay(
    title: String,
    chatId: Int,
    viewModel: ChatAppSharedViewModel,
    onClose: () -> Unit,
    onSendMessage: (String, Boolean) -> Unit,
) {

    val messages by viewModel.getMessagesForChat(chatId).collectAsState()
    var messageText by remember { mutableStateOf("") }
    var isSender by remember { mutableStateOf(true) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(onClick = onClose, indication = null, interactionSource = remember { MutableInteractionSource() })
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .clickable(enabled = false) {}, // Block clicks inside card from dismissing overlay
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header with title and close button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                HorizontalDivider()

                // Messages list
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        val isSender = msg.isSender

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (isSender) Color(0xFF1976D2) else Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = msg.content,
                                    color = if (isSender) Color.White else Color.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }


                // Input and send button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSender,
                        onCheckedChange = { isSender = it }
                    )
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type a message") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                onSendMessage(messageText, isSender)
                                messageText = ""
                            }
                        }
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }
}
