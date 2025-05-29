package com.readychatai.dev.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.readychatai.dev.presentation.common.TopBar
import com.readychatai.dev.presentation.main.BaseScreen
import com.readychatai.dev.ui.theme.screenBackgroundColor

@Composable
fun SettingsScreen(navController: NavHostController) {

    BaseScreen(
        navController = navController,
        topBar = {
            TopBar(
                title = "Settings",
            )
        },
    ) {
        SettingsScreenContent()
    }
}

@Composable
fun SettingsScreenContent(
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background(color = screenBackgroundColor)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState()),
        ) {
            // Your main screen content goes here
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}