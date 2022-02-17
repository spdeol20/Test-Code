package com.example.testrightmove.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@SuppressLint("NewApi")
class Connectivity @Inject constructor(@ApplicationContext val context:Context) {

    private val TAG ="Connectivity"
    val internetStatus = MutableStateFlow(Internet.INTERNET_AVAILABLE)
    val connectivityManager
            = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    @RequiresApi(Build.VERSION_CODES.M)
    val currentNetwork = connectivityManager.activeNetwork
    val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
    val linkProperties = connectivityManager.getLinkProperties(currentNetwork)

    private fun no_internet() {
        internetStatus.value = Internet.NO_INTERNET

    }

   private fun internet_available() {
        internetStatus.value = Internet.INTERNET_AVAILABLE

    }

    fun hasInternet():Boolean{
        return caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    fun noInternet():Boolean{
        return internetStatus.value == Internet.NO_INTERNET
    }

    init {
        Log.e(TAG, "initialise the respopnse helper: ")
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {
                Log.e(TAG, "onCapabilitiesChanged: has onAvailable")
                internet_available()
            }

            override fun onLost(network : Network) {
                Log.e(TAG, "onCapabilitiesChanged: has onLost")
                no_internet()
            }

            override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {

            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
            }
        })
    }
    fun unregisterNetworkCallback(){
        Log.e(TAG, "unregister callback of network ")
//        connectivityManager.unregisterNetworkCallback(callbackNetwork)
    }
}