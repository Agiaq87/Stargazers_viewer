package it.giaquinto.stargazersviewer.ui.state

import it.giaquinto.stargazersviewer.data.model.*

data class StargazersUiState(
    val userInteraction: UserInteraction,
    val userRequest: StargazersUserRequest?,
    val stargazersList: List<StargazersModel>?,
    val toastMessage: String?,
    val errorHintEditText: ErrorHintEditText?
)