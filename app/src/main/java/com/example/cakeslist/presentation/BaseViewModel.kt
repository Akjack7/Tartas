package com.example.cakeslist.presentation

import androidx.lifecycle.ViewModel
import com.example.cakeslist.utils.DispatcherFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    private val dispatcherFactory: DispatcherFactory
) : ViewModel(), CoroutineScope, KoinComponent {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcherFactory.getMain() + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}