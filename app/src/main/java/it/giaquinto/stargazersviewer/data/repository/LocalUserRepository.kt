package it.giaquinto.stargazersviewer.data.repository

import it.giaquinto.stargazersviewer.data.source.LocalUserDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserRepository @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) {

    val fetchIsUserLogged = localUserDataSource.fetchIsUserLogged()


}