package it.giaquinto.stargazersviewer.ui.state

import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import it.giaquinto.stargazersviewer.utils.info.InformationMessage

data class HomeUiState(
    val isSignedIn: Boolean,
    val isFetchingUsers: Boolean,
    val users: List<UserInfoModel>,
    val informationMessage: InformationMessage
)
