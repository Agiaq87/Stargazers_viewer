package it.giaquinto.stargazersviewer.data.api

sealed class ApiResult<out T> (val status: ApiStatus, val data: T?, val message:String?, statusCode: Int?) {

    data class Success<out R>(private val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null,
        statusCode = 200
    )

    data class Error(private val exception: String, private val _statusCode: Int?): ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception,
        statusCode = _statusCode
    )
}
