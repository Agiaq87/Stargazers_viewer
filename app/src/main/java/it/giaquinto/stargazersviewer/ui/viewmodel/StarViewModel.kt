package it.giaquinto.stargazersviewer.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.data.api.ApiResult
import it.giaquinto.stargazersviewer.data.model.ErrorHintEditText
import it.giaquinto.stargazersviewer.data.model.UserInteraction
import it.giaquinto.stargazersviewer.data.repository.StargazersRepository
import it.giaquinto.stargazersviewer.ui.state.StargazersUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarViewModel @Inject constructor(
    private val stargazersRepository: StargazersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        StargazersUiState(
            userInteraction = UserInteraction.NOTHING,
            userRequest = null,
            stargazersList = listOf(),
            toastMessage = null,
            errorHintEditText = ErrorHintEditText.NONE
        )
    )

    val uiState: StateFlow<StargazersUiState> = _uiState

    val userEditText = ObservableField<String?>()
    val repoEditText = ObservableField<String?>()
    val informationTextView = ObservableField("Searching...")

    fun validateRequest() {
        loadingState()

        userEditText.get()?.let { userEditText ->
            repoEditText.get()?.let { repoEditText ->
                viewModelScope.launch(Dispatchers.IO) {
                    makeRequest(userEditText, repoEditText)
                }
            } ?: run {
                repoError()
            }
        } ?: run {
            userError()
        }
    }

    private suspend fun makeRequest(user: String, repo: String) {
        informationTextView.set("Searching for $repo/$user ...")
        stargazersRepository.fetchStargazers(user, repo).collect { result ->
            when (result) {
                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            userInteraction = UserInteraction.NOTHING,
                            toastMessage = "${result._statusCode} - ${if (result.message?.isNotEmpty() == true) result.message else "Nothing to show"}"
                        )
                    }
                }
                is ApiResult.Success -> result.data?.let { list ->
                    if (list.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                userInteraction = UserInteraction.NOTHING,
                                stargazersList = list,
                                toastMessage = "Founded ${list.size} ${
                                    if (list.size == 1)
                                        "value"
                                    else
                                        "values"
                                } $repo/$user "
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                userInteraction = UserInteraction.NOTHING,
                                toastMessage = "Nothing to show :("
                            )
                        }
                    }
                } ?: run {
                    _uiState.update {
                        it.copy(
                            userInteraction = UserInteraction.NOTHING,
                            toastMessage = "Null result, please try again"
                        )
                    }
                }
            }
        }
    }

    private fun userError() = _uiState.update {
        it.copy(
            userInteraction = UserInteraction.NOTHING,
            errorHintEditText = ErrorHintEditText.USER,
            toastMessage = "Insert a user for searching"
        )
    }

    private fun repoError() = _uiState.update {
        it.copy(
            userInteraction = UserInteraction.NOTHING,
            errorHintEditText = ErrorHintEditText.REPO,
            toastMessage = "Insert a repo for searching"
        )
    }

    private fun loadingState() = _uiState.update {
        it.copy(
            userInteraction = UserInteraction.WAITING_RESPONSE,
            stargazersList = listOf()
        )
    }
}
