package it.giaquinto.stargazersviewer.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.data.exception.UnauthorizedException
import it.giaquinto.stargazersviewer.ui.state.HomeUiState
import it.giaquinto.stargazersviewer.data.repository.UserInfoRepository
import it.giaquinto.stargazersviewer.utils.info.InformationMessage
import it.giaquinto.stargazersviewer.utils.info.InformationType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            isSignedIn = false,
            isFetchingUsers = false,
            users = listOf(),
            informationMessage = InformationMessage("Nothing to show", InformationType.INFO)
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var _list: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val list: LiveData<List<String>>
        get() = _list

    private var fetchJob: Job? = null

    fun fetchUsers(searchData: String): Boolean {
        if (searchData.length < 4) {
            _uiState.update {
                it.copy(
                    informationMessage = InformationMessage(
                        context.getString(R.string.three_characters),
                        InformationType.INFO
                    )
                )
            }

            return true
        }

        fetchJob?.cancel()

        _uiState.update {
            it.copy(
                isFetchingUsers = true,
                informationMessage = InformationMessage(
                    "Searching...",
                    InformationType.INFO
                )
            )
        }

        fetchJob = viewModelScope.launch {
            userInfoRepository.fetchUserInfo(searchData).also {
                it.collect { userInfoRepository ->
                _uiState.update { homeUiState ->

                    if (userInfoRepository.isNotEmpty()) {
                        homeUiState.copy(
                            users = userInfoRepository,
                            informationMessage = InformationMessage(
                                "Founded ${userInfoRepository.size} ${if (userInfoRepository.size > 1) "user" else "users"}",
                                InformationType.INFO
                            )
                        )
                    } else {
                        homeUiState.copy(
                            isFetchingUsers = false,
                            users = userInfoRepository,
                            informationMessage = InformationMessage(
                                context.getString(R.string.nothing_found),
                                InformationType.WARNING
                            )
                        )
                    }

                }
                    it.catch {
                        _uiState.update { homeUiState ->
                            homeUiState.copy(
                                informationMessage = InformationMessage(
                                    it.localizedMessage ?: context.getString(R.string.network_exception),
                                    InformationType.ERROR
                                )
                            )
                        }
                    }
            }
            }

            try {

            } catch (ue: UnauthorizedException) {

            }
        }

        return true
    }
}