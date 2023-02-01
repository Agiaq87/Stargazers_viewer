package it.giaquinto.stargazersviewer.data.repository

import it.giaquinto.stargazersviewer.data.api.ApiResult
import it.giaquinto.stargazersviewer.data.api.UserInfoApi
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoRepository @Inject constructor(
    private val userInfoApi: UserInfoApi
) {
    fun fetchUserInfo(user: String): Flow<ApiResult<UserInfoModel>> = flow {
        emit(ApiResult.Loading(null, true))
        userInfoApi.fetchUserInfo(user).also {
            emit(ApiResult.Success(it))
        }
    }.flowOn(Dispatchers.IO)
}