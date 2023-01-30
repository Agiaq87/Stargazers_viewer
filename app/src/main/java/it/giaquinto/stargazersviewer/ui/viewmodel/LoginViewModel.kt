package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.data.repository.LocalUserRepository
import it.giaquinto.stargazersviewer.ui.state.LoginUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
): ViewModel() {
    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(
        LoginUiState(
            isLoading = false,
            errorMessage = null,
            loginOk = false
        )
    )

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            localUserRepository.fetchIsUserLogged.collect {
                _uiState.update { uiState ->
                    uiState.copy(loginOk = it)
                }
            }
        }
    }


}