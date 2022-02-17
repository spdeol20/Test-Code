package com.example.testrightmove.helper

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.testrightmove.util.Connectivity
import com.example.testrightmove.util.Loader
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ReponseHelper @Inject constructor() {
 private val TAG ="Response Helper"
    val loadingStatus = MutableStateFlow(Loader.LOAD)

    fun loading() {
        loadingStatus.value = Loader.LOAD

    }

      fun success() {
        loadingStatus.value = Loader.SUCESS

    }

      fun failure() {
        loadingStatus.value = Loader.FAIL
    }


}