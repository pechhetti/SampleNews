package com.example.samplenews.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable() : Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}