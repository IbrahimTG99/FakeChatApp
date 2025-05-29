package com.readychatai.dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.readychatai.dev.presentation.common.AlertDialog
import com.readychatai.dev.presentation.navigation.Navigation
import com.readychatai.dev.ui.theme.ReadyChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadyChatTheme {
                val showExitDialog = remember { mutableStateOf(false) }

                Navigation(
                    onBackPress = { showExitDialog.value = true },
                )
                AlertDialog(
                    showDialog = showExitDialog.value,
                    onDismiss = { showExitDialog.value = false },
                    onConfirm = {
                        showExitDialog.value = false
                        finish()
                    },
                    title = stringResource(R.string.confirmation_required),
                    message = stringResource(R.string.are_you_sure_you_want_to_proceed_with_this_action),
                    confirmText = "Confirm",
                    dismissText = "Cancel"
                )
            }
        }
    }
}
