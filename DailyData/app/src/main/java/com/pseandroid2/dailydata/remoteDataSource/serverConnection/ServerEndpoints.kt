package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import retrofit2.Call
import retrofit2.http.GET

interface ServerEndpoints
{
    @GET("greet")
    fun greet(): Call<String>
}