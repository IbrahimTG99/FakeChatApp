package com.readychatai.dev.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object Messages : Screens("messages_screen")

    @Serializable
    object Categories : Screens("categories_screen")

    @Serializable
    object Settings : Screens("settings_screen")

}
