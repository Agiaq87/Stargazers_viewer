package it.giaquinto.stargazersviewer.data.api

import it.giaquinto.stargazersviewer.data.model.RepoUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoUserApi {
    @GET("users/{user}/repos")
    suspend fun fetchRepoUser(@Path("user") user: String): Response<List<RepoUserModel>>
}
