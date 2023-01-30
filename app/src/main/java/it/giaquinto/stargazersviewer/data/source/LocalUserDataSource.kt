package it.giaquinto.stargazersviewer.data.source

import it.giaquinto.stargazersviewer.utils.persistence.PersistenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
    private val persistenceManager: PersistenceManager
) {
    fun fetchIsUserLogged(): Flow<Boolean> = flow {
        emit(persistenceManager.isUserLogged())
    }
}