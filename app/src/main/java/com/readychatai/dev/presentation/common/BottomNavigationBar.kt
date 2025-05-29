package com.readychatai.dev.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.readychatai.dev.presentation.navigation.Screens
import com.readychatai.dev.presentation.model.BottomBarNavItem

@Composable
fun BottomNavigationBar(
    navController: NavController?
) {
    val navigationItems = listOf(
        BottomBarNavItem(
            "Messages",
            Icons.Default.Email,
            Screens.Messages.route,
        ),
        BottomBarNavItem(
            "Categories",
            Icons.Default.AddCircle,
            Screens.Categories.route,
        ),
        BottomBarNavItem(
            "Settings",
            Icons.Default.Settings,
            Screens.Settings.route,
        ),
    )

    // Observe the current route
    val currentRoute = navController?.currentBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        navigationItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected, onClick = {
                    if (currentRoute != item.route) {
                        navController?.navigate(item.route) {
                            // Avoid building up a large back stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }, icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }, colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    BottomNavigationBar(navController = null)
}