package it.giaquinto.stargazersviewer.data.model

import com.google.gson.annotations.SerializedName

data class LocalUserModel(
    @SerializedName("email")
    val mail: String,
    @SerializedName("password")
    val password: String
)
