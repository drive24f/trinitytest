package com.trinity.test.utils

import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import com.trinity.test.rest.entity.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

fun <T> MutableLiveData<Resource<T>>.loading() = postValue(
    Resource(ResourceStatus.LOADING)
)

fun <T> MutableLiveData<Resource<T>>.hideLoading() = postValue(
    Resource(ResourceStatus.LOADING)
)

fun <T> MutableLiveData<Resource<T>>.loading(data: T? = null) = postValue(
    Resource(ResourceStatus.LOADING, data)
)

fun <T> MutableLiveData<Resource<T>>.success(data: T? = null) = postValue(
    Resource(ResourceStatus.SUCCESS, data)
)

fun <T> MutableLiveData<Resource<T>>.setError() = postValue(
    Resource(ResourceStatus.ERROR, data = null)
)

fun <T> MutableLiveData<Resource<T>>.setError(error: ErrorResponse? = null) = postValue(
    Resource(ResourceStatus.ERROR, value?.data, error = error)
)

fun <T> MutableLiveData<ResourceData<T>>.data(data: T) = postValue(ResourceData(data))

fun Throwable.getError(): ErrorResponse? {
    if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string()
        return when (response()?.code()) {
            in 500 until 599 -> processErrorResponse(errorBody.toString())
            401 -> processErrorResponse(errorBody.toString())
            403 -> processErrorResponse(errorBody.toString())
            404 -> processErrorResponse(errorBody.toString())
            405 -> processErrorResponse(errorBody.toString())
            else -> processErrorResponse(errorBody.toString())
        }
    }

    return ErrorResponse(
        code = 0,
        message = "there is an error",
        other = ""
    )
}

fun processErrorResponse(json: String): ErrorResponse? {
    val jsonErrorAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
    return jsonErrorAdapter.fromJson(json)
}

fun processErrorMessage(default: String, json: String): String {
    return try {
        val jsonErrorAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
        val error = jsonErrorAdapter.fromJson(json)
        error?.message.toString()
    } catch (ioException: IOException) {
        default
    }
}