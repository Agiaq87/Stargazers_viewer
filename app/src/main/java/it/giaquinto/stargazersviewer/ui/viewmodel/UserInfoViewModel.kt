package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.data.api.ApiResult
import it.giaquinto.stargazersviewer.data.repository.StargazersRepository
import it.giaquinto.stargazersviewer.ui.state.UserInfoUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val stargazersRepository: StargazersRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        UserInfoUiState(
            isFetchingUsers = true,
            toastMessage = null,
            userInfo = null,
            repoUserList = listOf()
        )
    )
    val uiState: StateFlow<UserInfoUiState> = _uiState

    fun fetchUser(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stargazersRepository.fetchUserInfo(user).collect {
                when(it) {
                    is ApiResult.Error -> TODO()
                    is ApiResult.Success -> _uiState.update {userInfoUiState ->
                        userInfoUiState.copy(
                            isFetchingUsers = false,
                            toastMessage = "Showing data for $user",
                            userInfo = it.data
                        )
                    }
                }
            }

            stargazersRepository.fetchRepoUser(user).collect {
                when(it) {
                    is ApiResult.Error -> _uiState.update {userInfoUiState ->
                        userInfoUiState.copy(
                            repoUserList = listOf()
                        )
                    }
                    is ApiResult.Success -> _uiState.update { userInfoUiState ->
                        userInfoUiState.copy(
                            repoUserList = it.data
                        )
                    }
                }
            }
        }
    }

}