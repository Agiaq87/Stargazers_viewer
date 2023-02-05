package it.giaquinto.stargazersviewer.di

import android.util.Log
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.giaquinto.stargazersviewer.data.api.RepoUserApi
import it.giaquinto.stargazersviewer.data.api.StargazersApi
import it.giaquinto.stargazersviewer.data.api.UserInfoApi
import it.giaquinto.stargazersviewer.utils.constant.HttpConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        readTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(
            HttpLoggingInterceptor { message ->
                Log.d(
                    "HTTPLOG",
                    message
                )
            }.also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
        )

        build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(httpClient: OkHttpClient): Retrofit = with(Retrofit.Builder()) {
        baseUrl(HttpConstants.BASE_URL)
        client(httpClient)
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        build()
    }

    @Singleton
    @Provides
    fun provideUserInfo(retrofit: Retrofit): UserInfoApi = retrofit.create(UserInfoApi::class.java)

    @Singleton
    @Provides
    fun provideStargazers(retrofit: Retrofit): StargazersApi = retrofit.create(StargazersApi::class.java)

    @Singleton
    @Provides
    fun provideRepoUser(retrofit: Retrofit): RepoUserApi = retrofit.create(RepoUserApi::class.java)
}