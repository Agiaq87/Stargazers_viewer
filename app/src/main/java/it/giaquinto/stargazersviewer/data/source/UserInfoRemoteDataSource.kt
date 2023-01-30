package it.giaquinto.stargazersviewer.data.source

import it.giaquinto.stargazersviewer.data.api.UserInfoApi
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class UserInfoRemoteDataSource @Inject constructor(
    private val userInfoApi: UserInfoApi
) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    fun fetchUserInfo(user: String): Flow<List<UserInfoModel>> = flow {
        emit(
            userInfoApi.fetchUserInfo(user)
        )
    }
}