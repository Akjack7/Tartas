package com.example.cakeslist.utils

import kotlin.coroutines.CoroutineContext

interface DispatcherFactory {
    fun getMain(): CoroutineContext
    fun getIO(): CoroutineContext
}