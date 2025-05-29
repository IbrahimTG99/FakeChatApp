package com.readychatai.dev.presentation.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import com.readychatai.dev.presentation.common.CategoryFilterRow
import com.readychatai.dev.presentation.common.TopBar
import com.readychatai.dev.presentation.main.BaseScreen
import com.readychatai.dev.presentation.model.MessageCategory
import com.readychatai.dev.presentation.model.MessageThread
import com.readychatai.dev.presentation.ChatAppSharedViewModel
import com.readychatai.dev.ui.theme.screenBackgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(navController: NavHostController, viewModel: ChatAppSharedViewModel = koinViewModel()) {
    BaseScreen(
        navController = navController,
        topBar = {
            TopBar(
                title = "Messages",
                showSearch = true,
                showMenu = true,
                showAdd = true,
                onSearchClick = { /* Handle search click */ },
                onAddClick = {
                    viewModel.addChat(
                        Chat(
                            title = "New Chat",
                        )
                    )
                }
            )
        },
    ) {
        val categories by viewModel.categories.collectAsState()
        val chats by viewModel.chats.collectAsState()
        ChatScreenContent(
            categories,
            chats
        )
    }
}

@Composable
fun ChatScreenContent(
    categories: List<Category>,
    chats: List<ChatWithMessagesAndCategories>,
    viewModel: ChatAppSharedViewModel = koinViewModel()
) {
    val messageCategories = remember(categories) {
        buildList {
            add(MessageCategory("all", "All", isActive = true))
            addAll(categories.map { MessageCategory(it.name.lowercase(), it.name) })
        }
    }

    var selectedCategory by remember { mutableStateOf("all") }
    var selectedChat by remember { mutableStateOf<ChatWithMessagesAndCategories?>(null) }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(color = screenBackgroundColor)
                    .fillMaxSize(),
            ) {
                CategoryFilterRow(
                    categories = messageCategories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                val filteredThreads = remember(chats, selectedCategory) {
                    chats.filter { chat ->
                        selectedCategory == "all" ||
                                chat.categories.any {
                                    it.name.equals(selectedCategory, ignoreCase = true)
                                }
                    }.map { chat ->
                        val latestMessage = chat.messages.lastOrNull()
                        MessageThread(
                            senderName = chat.chat.title,
                            message = latestMessage?.content.orEmpty(),
                            time = (latestMessage?.timestamp ?: "").toString(),
                            category = chat.categories.joinToString(", ") { it.name },
                            id = chat.chat.id.toString()
                        )
                    }
                }

                MessageThreadsList(
                    threads = filteredThreads,
                    selectedCategory = selectedCategory,
                    onThreadClick = { thread ->
                        val chat = chats.find { it.chat.id.toString() == thread.id }
                        chat?.let { selectedChat = it }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Overlay
            selectedChat?.let { chat ->
                ChatOverlay(
                    title = chat.chat.title,
                    chatId = chat.chat.id,
                    onClose = { selectedChat = null },
                    onSendMessage = { msg ->
                        viewModel.addMessage(chat.chat.id, msg)
                    },
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun MessageThreadsList(
    threads: List<MessageThread>,
    selectedCategory: String,
    onThreadClick: (MessageThread) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredThreads = remember(threads, selectedCategory) {
        if (selectedCategory == "all") threads
        else threads.filter { it.category.contains(selectedCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(filteredThreads) { thread ->
            MessageThreadItem(
                thread = thread,
                onClick = { onThreadClick(thread) }
            )
        }
    }
}

@Composable
private fun MessageThreadItem(
    thread: MessageThread, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.White,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            AvatarView(
                name = thread.senderName, backgroundColor = thread.avatarColor, size = 48.dp
            )

            // Message content
            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = thread.senderName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = thread.time,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = thread.message,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = thread.category,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun AvatarView(
    name: String, backgroundColor: Color, size: Dp, modifier: Modifier = Modifier
) {
    val initials = remember(name) {
        name.split(" ").take(2).joinToString("") { it.firstOrNull()?.toString() ?: "" }.uppercase()
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = (size.value * 0.4).sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
