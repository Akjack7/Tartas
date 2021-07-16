package com.example.cakeslist.data

import com.example.cakeslist.data.models.Cake
import retrofit2.http.GET

interface CakesApi {

    //todo pagination
    @GET("cakes")
    suspend fun getCakes(
    ): List<Cake>

}