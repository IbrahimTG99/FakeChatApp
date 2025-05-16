package com.readychatai.dev.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.readychatai.dev.ui.navigation.model.BottomNavItem
import com.readychatai.dev.ui.screen.categories.CategoryScreen
import com.readychatai.dev.ui.screen.messages.MessagesScreen

@Composable
fun MyNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Chat.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomNavItem.Chat.route) {
            MessagesScreen()
        }
        composable(BottomNavItem.Category.route) {
            CategoryScreen()
        }
    }
}
