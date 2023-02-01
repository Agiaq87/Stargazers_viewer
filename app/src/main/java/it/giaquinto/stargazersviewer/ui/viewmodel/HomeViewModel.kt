package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.data.api.ApiStatus
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import it.giaquinto.stargazersviewer.data.model.info.InformationMessage
import it.giaquinto.stargazersviewer.data.model.info.InformationType
import it.giaquinto.stargazersviewer.data.repository.UserInfoRepository
import it.giaquinto.stargazersviewer.ui.state.HomeUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        HomeUiState(
            isSignedIn = false,
            isFetchingUsers = false,
            users = listOf(),
            informationMessage = InformationMessage("Waiting for input", InformationType.INFO)
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState

    private var _list: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val list: LiveData<List<String>>
        get() = _list

    private var fetchJob: Job? = null

    fun fetchUsers(searchData: String): Boolean {
        if (searchData.length < 4) {
            _uiState.update {
                it.copy(
                    informationMessage = InformationMessage(
                        "Please insert at least three characters for searching",
                        InformationType.INFO
                    )
                )
            }

            return true
        }

        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            userInfoRepository.fetchUserInfo(searchData).collect { userInfoRepository ->
                when (userInfoRepository.status) {
                    ApiStatus.LOADING -> loading()
                    ApiStatus.SUCCESS -> {
                        userInfoRepository.data?.let { list ->
                            foundIt(listOf(list))
                        } ?: run {
                            nothingToShow()
                        }
                    }
                    ApiStatus.ERROR -> networkError(userInfoRepository.message)
                }
            }
        }

        return true
    }

    private fun loading() = _uiState.update {
        it.copy(
            isFetchingUsers = true,
            informationMessage = InformationMessage(
                "Searching...",
                InformationType.INFO
            )
        )
    }

    private fun nothingToShow() = _uiState.update {
        it.copy(
            isFetchingUsers = false,
            informationMessage = InformationMessage(
                "Nothing to show",
                InformationType.WARNING
            )
        )
    }

    private fun foundIt(list: List<UserInfoModel>) = _uiState.update {
        it.copy(
            isFetchingUsers = false,
            users = list,
            informationMessage = InformationMessage(
                "Founded ${list.size} ${if (list.size > 1) "user" else "users"}",
                InformationType.WARNING
            )
        )
    }

    private fun networkError(message: String?) = _uiState.update { homeUiState ->
        homeUiState.copy(
            informationMessage = InformationMessage(
                message ?: "Network error, please try again later",
                InformationType.ERROR
            )
        )
    }
}