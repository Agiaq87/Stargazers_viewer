package it.giaquinto.stargazersviewer.ui.state

data class LoginUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val loginOk: Boolean
)