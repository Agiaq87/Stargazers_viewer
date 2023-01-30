package it.giaquinto.stargazersviewer.data.api

import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfoApi {
    @GET("users/{user}")
    suspend fun fetchUserInfo(@Path("user") user: String): List<UserInfoModel>
}