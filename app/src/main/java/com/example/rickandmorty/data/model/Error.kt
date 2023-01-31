package com.example.rickandmorty.data.model

sealed class ResultError : Exception()

object Empty : ResultError()

sealed class RetrofitError : ResultError() {
    object EmptyBody : ResultError()
    class HttpException(val description: String) : RetrofitError()
    class Network(val description: String) : RetrofitError()
    class Timeout(val description: String) : RetrofitError()
    class Unknown(val description: String) : RetrofitError()
}

sealed class HttpCodeError : ResultError() {
    object ServerNoContent : HttpCodeError() // 204
    object BadRequest : HttpCodeError() // 400
    object Unauthorized : HttpCodeError() // 401
    object Forbidden : HttpCodeError() // 403
    object NotFound : HttpCodeError() // 404
    object NotAcceptable : HttpCodeError() // 406
    object Timeout : HttpCodeError() // 408
    object ServerConflict : HttpCodeError() // 409
    object InternalServerError : HttpCodeError() // 500
}

object NoInternetError : ResultError()

fun ResultError.isEmpty(): Boolean = this is Empty
