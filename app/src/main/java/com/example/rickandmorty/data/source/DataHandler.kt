package com.example.rickandmorty.data.source

import com.example.rickandmorty.data.model.HttpCodeError
import com.example.rickandmorty.data.model.ResultError
import com.example.rickandmorty.data.model.RetrofitError
import com.example.rickandmorty.helpers.Constants
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiHandler(call: suspend () -> Response<T>): T {
    try {
        val response = call()
        response.body()?.let { body ->
            if (response.isSuccessful) {
                return body
            } else {
                throw response.code().toHttpError()
            }
        } ?: throw RetrofitError.EmptyBody
    } catch (e: Exception) {
        when (e) {
            is HttpException -> {
                val body = e.response()?.errorBody().toString()
                throw RetrofitError.HttpException(body)
            }
            is SocketTimeoutException -> throw RetrofitError.Timeout("Timeout Error")
            is IOException -> throw RetrofitError.Network("Thread Error")
            is ResultError -> throw e
            else -> throw RetrofitError.Unknown("Unknown Error")
        }
    }
}

fun Int.toHttpError() =
    when (this) {
        Constants.SERVER_NO_CONTENT_CODE -> HttpCodeError.ServerNoContent
        Constants.SERVER_BAD_REQUEST_CODE -> HttpCodeError.BadRequest
        Constants.SERVER_UNAUTHORIZED_CODE -> HttpCodeError.Unauthorized
        Constants.SERVER_FORBIDDEN_CODE -> HttpCodeError.Forbidden
        Constants.SERVER_NOT_FOUND_CODE -> HttpCodeError.NotFound
        Constants.SERVER_NOT_ACCEPTABLE_CODE -> HttpCodeError.NotAcceptable
        Constants.SERVER_TIMEOUT_CODE -> HttpCodeError.Timeout
        Constants.SERVER_CONFLICT_CODE -> HttpCodeError.ServerConflict
        else -> HttpCodeError.InternalServerError
    }
