package com.example.rickandmorty.helpers.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.HttpCodeError
import com.example.rickandmorty.data.model.NoInternetError
import com.example.rickandmorty.data.model.ResultError
import com.example.rickandmorty.data.model.RetrofitError

val Context.isNetworkAvailable: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.toast(@StringRes resId: Int) {
    toast(getString(resId))
}

fun Context.handleError(error: ResultError) {
    when (error) {
        is HttpCodeError.NotFound -> toast(R.string.error_not_found)
        is HttpCodeError.InternalServerError -> toast(
            R.string.error_internal_server
        )
        is NoInternetError -> toast(R.string.error_internet_connection)
        is RetrofitError.HttpException -> toast("Error HTTP")
        else -> {}
    }
}
