package it.giaquinto.stargazersviewer.data.api

import it.giaquinto.stargazersviewer.data.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface Api {
    @GET("users/{user}")
    suspend fun userInfo(@Path("user") user: String): List<UserInfo>
}