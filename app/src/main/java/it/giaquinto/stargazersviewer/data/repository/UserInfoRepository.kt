package it.giaquinto.stargazersviewer.data.repository

import it.giaquinto.stargazersviewer.data.api.UserInfoApi
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import it.giaquinto.stargazersviewer.data.source.UserInfoRemoteDataSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoRepository @Inject constructor(
    private val userInfoRemoteDataSource: UserInfoRemoteDataSource
) {
    fun fetchUserInfo(user: String): Flow<List<UserInfoModel>> =
        userInfoRemoteDataSource.fetchUserInfo(user)
            .onEach {  }
            .catch {
            exception -> emit(listOf())
        }
}