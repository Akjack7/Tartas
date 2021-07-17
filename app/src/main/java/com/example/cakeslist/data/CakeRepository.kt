package com.example.cakeslist.data

import com.example.cakeslist.data.models.Cake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CakeRepository(private val cakesApi: CakesApi) {
    fun getCakes(): Flow<List<Cake>> = flow {
        emit(cakesApi.getCakes().distinctBy { it.title }.sortedBy { it.title })
    }
}