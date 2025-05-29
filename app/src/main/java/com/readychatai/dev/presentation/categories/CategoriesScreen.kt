package com.readychatai.dev.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.readychatai.dev.data.room.entities.Category
import com.readychatai.dev.presentation.common.TopBar
import com.readychatai.dev.presentation.main.BaseScreen
import com.readychatai.dev.presentation.ChatAppSharedViewModel
import com.readychatai.dev.ui.theme.screenBackgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(navController: NavHostController) {
    val viewModel: ChatAppSharedViewModel = koinViewModel()
    var showDialog by remember { mutableStateOf(false) }

    BaseScreen(
        navController = navController,
        topBar = {
            TopBar(
                title = "Categories",
                showAdd = true,
                onAddClick = { showDialog = true }
            )
        },
    ) {
        CategoriesScreenContent(viewModel = viewModel)
    }

    if (showDialog) {
        AddCategoryDialog(
            onDismiss = { showDialog = false },
            onAdd = { category ->
                viewModel.addCategory(category)
                showDialog = false
            }
        )
    }
}

@Composable
fun CategoriesScreenContent(viewModel: ChatAppSharedViewModel) {
    val categories by viewModel.categories.collectAsState()

    LazyColumn(
        modifier = Modifier
            .background(screenBackgroundColor)
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items = categories, key = { it.id }) { category ->
            CategoryCard(
                category = category,
                onEditClick = {
                    // TODO: Implement edit dialog
                }
            )
        }
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onAdd: (Category) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var keywords by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Category") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Category Name") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = keywords,
                    onValueChange = { keywords = it },
                    label = { Text("Comma-separated Keywords") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val keywordSet = keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toSet()
                onAdd(Category(keywords = keywordSet.toList(), name = name))
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    CategoriesScreen(navController)
}