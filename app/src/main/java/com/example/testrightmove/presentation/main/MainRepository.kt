package com.example.testrightmove.presentation.main

import com.example.testrightmove.network.service.ApiService

class MainRepository(private val apiService:ApiService) {
    suspend fun getApiData() = apiService.getData()
}