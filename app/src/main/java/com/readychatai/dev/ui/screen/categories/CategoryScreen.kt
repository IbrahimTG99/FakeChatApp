package com.readychatai.dev.ui.screen.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.readychatai.dev.domain.model.Category
import com.readychatai.dev.ui.screen.components.CategoryCard

@Composable
fun CategoryScreen() {
    val categories = listOf(
        Category(setOf("family", "friends"), "Personal", 1),
        Category(setOf("work"), "Work", 2),
        Category(setOf("travel"), "Travel", 3)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Categories",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(categories.size) { index ->
                CategoryCard(category = categories[index], onEditClick = {})
            }
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(24.dp).fillMaxWidth()
                .height(48.dp)
                .align(Alignment.End),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = "Add Category")
        }
    }
}
