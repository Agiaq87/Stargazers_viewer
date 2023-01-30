package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.data.repository.LocalUserRepository
import it.giaquinto.stargazersviewer.ui.state.MainActivityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainActivityUiState> = MutableStateFlow(
        MainActivityUiState(isUserLogged = false, isLoading = false)
    )

    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        viewModelScope.launch {
            localUserRepository.fetchIsUserLogged.collect {
                _uiState.update { uiState ->
                    uiState.copy(isUserLogged = it)
                }
            }
        }
    }

    fun isLoading(): Boolean = _uiState.value.isLoading
}