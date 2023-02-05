package it.giaquinto.stargazersviewer.data.api

import it.giaquinto.stargazersviewer.data.model.GitHubApiErrorModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

enum class ApiStatus {
    SUCCESS,
    ERROR
}