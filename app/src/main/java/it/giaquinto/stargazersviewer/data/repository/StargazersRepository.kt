package it.giaquinto.stargazersviewer.data.repository

import it.giaquinto.stargazersviewer.data.api.ApiResult
import it.giaquinto.stargazersviewer.data.api.RepoUserApi
import it.giaquinto.stargazersviewer.data.api.StargazersApi
import it.giaquinto.stargazersviewer.data.api.UserInfoApi
import it.giaquinto.stargazersviewer.data.model.RepoUserModel
import it.giaquinto.stargazersviewer.data.model.StargazersModel
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StargazersRepository @Inject constructor(
    private val stargazersApi: StargazersApi,
    private val userInfoApi: UserInfoApi,
    private val repoUserApi: RepoUserApi
) {

    fun fetchStargazers(
        user: String,
        repo: String
    ): Flow<ApiResult<List<StargazersModel>>> = flow {
        stargazersApi.fetchStargazers(repo, user).also {
            emit(
                when (it.code()) {
                    200 -> ApiResult.Success(_data = it.body())
                    else -> ApiResult.Error(exception = it.message(), _statusCode = it.code())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

    fun fetchUserInfo(user: String): Flow<ApiResult<UserInfoModel>> = flow {
        userInfoApi.fetchUserInfo(user).also {
            emit(
                ApiResult.Success(it)
            )
        }
    }.flowOn(Dispatchers.IO)

    fun fetchRepoUser(user: String): Flow<ApiResult<List<RepoUserModel>>> = flow {
        repoUserApi.fetchRepoUser(user).also {
            emit(
                when(it.code()) {
                    200 -> ApiResult.Success(_data = it.body())
                    else -> ApiResult.Error(exception = it.message(), _statusCode = it.code())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

}
