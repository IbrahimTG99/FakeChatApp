package com.readychatai.dev.ui.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.readychatai.dev.R

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Chat : BottomNavItem("chat", "Chat", Icons.Default.Email)
    object Category : BottomNavItem("category", "Category", Icons.Default.Settings)
}
