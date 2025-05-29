package com.readychatai.dev.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.readychatai.dev.presentation.categories.CategoriesScreen
import com.readychatai.dev.presentation.main.BaseScreen
import com.readychatai.dev.presentation.messages.ChatScreen
import com.readychatai.dev.presentation.settings.SettingsScreen


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    onBackPress: () -> Unit,
) {
    val currentBackStack by navController.currentBackStackEntryAsState()

    BackHandler {
        val isAtRoot = currentBackStack?.destination?.route == Screens.Messages.route
        if (isAtRoot) {
            onBackPress()
        } else {
            navController.navigateUp()
        }
    }

    val graph = navController.createGraph(startDestination = Screens.Messages.route) {
        composable(route = Screens.Messages.route) {
            BaseScreen(
                navController,
                {
                    ChatScreen(navController)
                },
            )
        }
        composable(route = Screens.Categories.route) {
            BaseScreen(
                navController,
                {
                    CategoriesScreen(navController)
                },
            )

        }
        composable(route = Screens.Settings.route) {
            BaseScreen(
                navController,
                {
                    SettingsScreen(navController)
                },
            )

        }
    }

    NavHost(
        navController = navController,
        graph = graph,
        modifier = Modifier,
    )
}
