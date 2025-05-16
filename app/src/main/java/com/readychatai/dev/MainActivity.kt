package com.readychatai.dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.readychatai.dev.ui.navigation.MyNavHost
import com.readychatai.dev.ui.navigation.model.BottomBar
import com.readychatai.dev.ui.navigation.model.BottomNavItem
import com.readychatai.dev.ui.theme.ReadyChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadyChatTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: BottomNavItem.Chat.route
    Scaffold(
        bottomBar = {
            BottomBar(currentRoute) { navItem ->
                if (currentRoute != navItem.route) {
                    navController.navigate(navItem.route) {
                        popUpTo(BottomNavItem.Chat.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }
    ) { paddingValues ->
        MyNavHost(navController, paddingValues)
    }
}
