package it.giaquinto.stargazersviewer.utils.persistence

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PersistenceManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun isUserLogged(): Boolean = sharedPreferences.getBoolean(IS_USER_LOGGED, false)
    fun registerUserLogged() = editor.putBoolean(IS_USER_LOGGED, true).apply()

    internal companion object {
        const val NAME = "STARGAZERS_PERSISTENCE"
        const val IS_USER_LOGGED = "${NAME}_IS_USER_LOGGED"
    }
}