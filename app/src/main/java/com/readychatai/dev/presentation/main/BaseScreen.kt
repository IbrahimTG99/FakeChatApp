package com.readychatai.dev.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.readychatai.dev.presentation.common.BottomNavigationBar
import com.readychatai.dev.presentation.common.TopBar
import com.readychatai.dev.ui.theme.screenBackgroundColor

@Composable
fun BaseScreen(
    navController: NavHostController,
    topBar: @Composable () -> Unit = { TopBar("") },
    screen: @Composable () -> Unit = {},
) {

    Scaffold(
        topBar = topBar,
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = Modifier
                .background(color = screenBackgroundColor)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                screen()
            }
        }
    }

}