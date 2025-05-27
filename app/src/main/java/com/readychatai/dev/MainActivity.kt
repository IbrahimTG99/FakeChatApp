package com.readychatai.dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.data.room.entities.Chat
import com.readychatai.dev.data.room.entities.Message
import com.readychatai.dev.data.room.relations.ChatWithMessagesAndCategories
import com.readychatai.dev.ui.navigation.MyNavHost
import com.readychatai.dev.ui.navigation.model.BottomBar
import com.readychatai.dev.ui.navigation.model.BottomNavItem
import com.readychatai.dev.ui.screen.chat.ChatViewModel
import com.readychatai.dev.ui.theme.ReadyChatTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadyChatTheme {
//                val navController = rememberNavController()
//                MainScreen(navController)
                ChatScreen()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: BottomNavItem.Chat.route
    Scaffold(
        bottomBar = {
            BottomBar(currentRoute) { navItem ->
                if (currentRoute != navItem.route) {
                    navController.navigate(navItem.route) {
                        popUpTo(BottomNavItem.Chat.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }
    ) { paddingValues ->
        MyNavHost(navController, paddingValues)
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel = koinViewModel()) {

    val chats by viewModel.chats.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var selectedChatId by remember { mutableStateOf<Int?>(null) }
    var messageContent by remember { mutableStateOf("") }
    var messageSender by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }
    var categoryKeyword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Chats", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories.size) { index ->
                val category = categories[index]
                Text(category.name, modifier = Modifier.padding(end = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chats.size) { index ->
                val chat = chats[index]
                ChatItem(chat = chat)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Chat
        AddChatButton(viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        // Add Message to Selected Chat
        Text("Add Message", style = MaterialTheme.typography.titleSmall)

        DropdownMenuForChats(chats = chats, selectedChatId = selectedChatId) {
            selectedChatId = it
        }

        BasicTextField(
            value = messageContent,
            onValueChange = { messageContent = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    if (messageContent.isEmpty()) Text("Message content")
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = messageSender,
            onValueChange = { messageSender = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    if (messageSender.isEmpty()) Text("Sender name")
                    innerTextField()
                }
            }
        )

        Button(
            onClick = {
                selectedChatId?.let { chatId ->
                    val message = Message(chatId = chatId, content = messageContent, timestamp = System.currentTimeMillis(), sender = messageSender)
                    viewModel.addMessage(message)
                    messageContent = ""
                    messageSender = ""
                }
            },
            enabled = selectedChatId != null && messageContent.isNotBlank() && messageSender.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Category
        Text("Add Category", style = MaterialTheme.typography.titleSmall)

        BasicTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    if (categoryName.isEmpty()) Text("Enter category name")
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = categoryKeyword,
            onValueChange = { categoryKeyword = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    if (categoryKeyword.isEmpty()) Text("Enter category name")
                    innerTextField()
                }
            }
        )

        Button(
            onClick = {
                if (categoryName.isNotBlank()) {
                    viewModel.addCategory(Category(
                        name = categoryName,
                        keywords = listOf(categoryKeyword)
                    ))
                    categoryName = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Category")
        }
    }
}

@Composable
fun DropdownMenuForChats(
    chats: List<ChatWithMessagesAndCategories>,
    selectedChatId: Int?,
    onChatSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedChatId?.let { id ->
                chats.find { it.chat.id == id }?.chat?.title ?: "Select Chat"
            } ?: "Select Chat")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            chats.forEach { chat ->
                DropdownMenuItem(
                    text = { Text(chat.chat.title) },
                    onClick = {
                        onChatSelected(chat.chat.id)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun ChatItem(chat: ChatWithMessagesAndCategories) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Chat: ${chat.chat.title}", style = MaterialTheme.typography.bodyLarge)

        Column(modifier = Modifier.fillMaxWidth()) {
            chat.messages.forEach { message ->
                Text(text = "Message: ${message.content} - Sent by ${message.sender}")
            }
        }

        Text(text = "Categories: ${chat.categories.joinToString(", ") { it.name }}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AddChatButton(viewModel: ChatViewModel) {
    var chatTitle by remember { mutableStateOf("") }

    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = chatTitle,
            onValueChange = { chatTitle = it },
            modifier = Modifier.weight(1f).padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    if (chatTitle.isEmpty()) {
                        Text("Enter chat title")
                    }
                    innerTextField()
                }
            }
        )

        Button(onClick = {
            if (chatTitle.isNotEmpty()) {
                val newChat = Chat(title = chatTitle)
                viewModel.addChat(newChat)
                chatTitle = ""
            }
        }) {
            Text(text = "Add Chat")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChatScreen()
}
