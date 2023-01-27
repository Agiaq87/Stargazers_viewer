package it.giaquinto.stargazersviewer.data.repository

import it.giaquinto.stargazersviewer.data.api.Api
import it.giaquinto.stargazersviewer.data.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoRepository @Inject constructor(private val api: Api) {

    fun getUserInfo(user: String): Flow<List<UserInfo>> =
        flow<List<UserInfo>> {
            emit(api.userInfo(user))
        }
}