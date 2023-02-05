package it.giaquinto.stargazersviewer.data.model
import com.google.gson.annotations.SerializedName


data class GitHubApiErrorModel(
    @SerializedName("documentation_url")
    val documentationUrl: String,
    @SerializedName("message")
    val message: String
)