package com.readychatai.dev.presentation.categories

import android.util.Log
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
import com.readychatai.dev.ui.theme.screenBackgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(navController: NavHostController) {
    val viewModel: CategoriesViewModel = koinViewModel()
    var showDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

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
        CategoriesScreenContent(viewModel = viewModel) {
            selectedCategory = it
            showDialog = true
        }
    }

    if (showDialog) {
        AddCategoryDialog(
            id = selectedCategory?.id ?: 0,
            name = selectedCategory?.name.orEmpty(),
            keywords = selectedCategory?.keywords?.let{
                it.joinToString(",") {
                    it.trim()
                }}.orEmpty(),
            onDismiss = { showDialog = false },
            onAdd = { category ->
                viewModel.addOrUpdateCategory(category, selectedCategory != null)
                selectedCategory = null
                showDialog = false
                Log.d("CategoryCard up", "Edit button clicked for category: ${category.name}")
            }
        )
    }
}

@Composable
fun CategoriesScreenContent(viewModel: CategoriesViewModel, onEditClick: (Category) -> Unit) {
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
                    onEditClick(category)
                    Log.d("CategoryCard", "Edit button clicked for category: ${category.name}")
                }
            )
        }
    }
}

@Composable
fun AddCategoryDialog(
    id: Int = 0,
    name: String = "",
    keywords: String = "",
    onDismiss: () -> Unit,
    onAdd: (Category) -> Unit
) {
    var name by remember { mutableStateOf(name) }
    var keywords by remember { mutableStateOf(keywords) }

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
                onAdd(Category(id = id, keywords = keywordSet.toList(), name = name))
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