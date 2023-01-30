package it.giaquinto.stargazersviewer.data.exception

class UnauthorizedException: Exception() {

    override fun getLocalizedMessage(): String? {
        return "Can not retrieve information without sign in"
    }
}