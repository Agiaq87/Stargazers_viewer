package it.giaquinto.stargazersviewer.di.provider

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.giaquinto.stargazersviewer.utils.constant.HttpConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpProvider {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        readTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(HttpConstants.TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(
            HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("HTTPLOG", message)
                    }
                }
            ).also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
        )

        build()
    }
}