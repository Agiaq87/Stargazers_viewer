package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.ui.state.MainActivityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<MainActivityUiState> = MutableStateFlow(
        MainActivityUiState(isUserLogged = false, isLoading = false)
    )

    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        viewModelScope.launch {

        }
    }

    fun isLoading(): Boolean = _uiState.value.isLoading
}