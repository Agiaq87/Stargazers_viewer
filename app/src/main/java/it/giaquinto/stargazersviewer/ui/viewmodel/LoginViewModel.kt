package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import it.giaquinto.stargazersviewer.ui.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {
    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(
        LoginUiState(
            isLoading = false,
            errorMessage = null,
            loginOk = false
        )
    )

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


}