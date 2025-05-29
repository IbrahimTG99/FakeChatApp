package com.readychatai.dev.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.readychatai.dev.data.room.entities.Category

@Composable
fun CategoryCard(
    category: Category,
    onEditClick: (Category) -> Unit
) {
    val tags = category.keywords.toList()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header: Category name and Edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { onEditClick(category) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit category"
                    )
                }
            }

            // Tags
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    count = tags.size,
                    key = { index -> "${category.id}-${tags[index]}" }
                ) { index ->
                    AssistChip(
                        onClick = { onEditClick(category) },
                        label = {
                            Text(text = tags[index])
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    val category = Category(keywords = listOf("family", "friends"), name = "Personal", id = 1)
    CategoryCard(category = category, {})
}
