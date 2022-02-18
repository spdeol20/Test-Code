package com.example.testrightmove.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrightmove.helper.ReponseHelper
import com.example.testrightmove.model.PropertiesItem
import com.example.testrightmove.util.Connectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    val connectivity: Connectivity,
    val helper: ReponseHelper
) : ViewModel() {
    private val TAG = "viewmodel"
    private val propertiesStateFlowData = MutableStateFlow<List<PropertiesItem?>?>(null)
    private val averagePrice = MutableStateFlow<Int>(0)

    fun getProperties() = propertiesStateFlowData
    fun getAveragePrice() = averagePrice.asStateFlow()

    init {
        loadProperties()
    }
//    fun internetStatus():MutableStateFlow<Internet.INTERNET_AVAILABLE = connectivity.internetStatus
//
//    fun loadingStatus() = helper.loadingStatus


    fun loadProperties() {

        if (connectivity.hasInternet()) {
            helper.loading()
            viewModelScope.launch {
                var data = repository.getApiData()
                if (data?.isSuccessful == true) {
                    helper.success()
                    propertiesStateFlowData.value = data.body()?.properties
                    calculatePrice(data.body()?.properties)
                } else {
                    helper.failure()

                }
            }
        } else {
            connectivity.no_internet()
        }


    }

    override fun onCleared() {
        super.onCleared()
        connectivity.unregisterNetworkCallback()
    }

    private fun calculatePrice(properties: List<PropertiesItem?>?): Boolean {
        var totalPricelist = 0
        val totalHouses = properties?.size ?: 0
        properties?.forEach { item ->
            totalPricelist += item?.price ?: 0
        }
        val average = totalPricelist / totalHouses
        averagePrice.value = (average)
        return false

    }

    @TestOnly
    fun loadTestData(properties: List<PropertiesItem?>?): Boolean {
        return calculatePrice(properties)
    }
    @TestOnly
    fun test(): Boolean {
        return false
    }
}