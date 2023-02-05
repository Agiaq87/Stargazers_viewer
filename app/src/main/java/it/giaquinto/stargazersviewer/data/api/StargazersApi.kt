package it.giaquinto.stargazersviewer.data.api

import it.giaquinto.stargazersviewer.data.model.StargazersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StargazersApi {
    @GET("repos/{repo}/{user}/stargazers")
    suspend fun fetchStargazers(@Path("repo") repo: String, @Path("user") user: String): Response<List<StargazersModel>>
}
