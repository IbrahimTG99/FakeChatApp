package com.readychatai.dev.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showSearch: Boolean = false,
    showMenu: Boolean = false,
    showAdd: Boolean = false,
    onSearchClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Column {
        TopAppBar(
            title = {
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }, actions = {
            if (showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        Icons.Default.Search, "Search",
                        tint = Color.Gray,
                    )
                }
            }
            if (showMenu) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.AutoMirrored.Filled.List, "Menu",
                        tint = Color.Gray,
                    )
                }
            }

            if (showAdd) {
                IconButton(onClick = onAddClick) {
                    Icon(
                        Icons.Default.Add, "Add",
                        tint = Color.Gray,
                    )
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
        )
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        title = "Messages", showSearch = true, showAdd = true
    )
}