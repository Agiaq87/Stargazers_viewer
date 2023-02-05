package it.giaquinto.stargazersviewer.ui.state

import it.giaquinto.stargazersviewer.data.model.RepoUserModel
import it.giaquinto.stargazersviewer.data.model.UserInfoModel


data class UserInfoUiState(
    val isFetchingUsers: Boolean,
    val toastMessage: String?,
    val userInfo: UserInfoModel?,
    val repoUserList: List<RepoUserModel>?
)
