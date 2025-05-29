package com.readychatai.dev.presentation.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessagesViewModel : ViewModel() {

    // UI State
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    // Event channel for one-time events
    private val _homeScreenEvent = Channel<HomeScreenEvent>()
    val homeScreenEvent = _homeScreenEvent.receiveAsFlow()

    // Centralized action handler
    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.NavigateToQRGenerator -> {
                viewModelScope.launch {
                    _homeScreenEvent.send(HomeScreenEvent.NavigateToQRGeneratorScreen)
                }
            }

            is HomeScreenAction.NavigateToQRScanner -> {
                viewModelScope.launch {
                    _homeScreenEvent.send(HomeScreenEvent.NavigateToQRScannerScreen)
                }
            }

            is HomeScreenAction.ToggleFab -> {
                _state.update { it.copy(isFabExpanded = !it.isFabExpanded) }
            }
        }
    }

    // State class
    data class HomeScreenState(
        val isFabExpanded: Boolean = false
    )

    // Events
    sealed interface HomeScreenEvent {
        object NavigateToQRGeneratorScreen : HomeScreenEvent
        object NavigateToQRScannerScreen : HomeScreenEvent
    }

    // Actions
    sealed interface HomeScreenAction {
        object NavigateToQRGenerator : HomeScreenAction
        object NavigateToQRScanner : HomeScreenAction
        object ToggleFab : HomeScreenAction
    }
}