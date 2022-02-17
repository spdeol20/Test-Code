package com.example.testrightmove.network.service

import com.example.testrightmove.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("master/properties.json")
    suspend fun getData():Response<ResponseModel>?
}