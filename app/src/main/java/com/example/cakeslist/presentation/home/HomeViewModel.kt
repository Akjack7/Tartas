package com.example.cakeslist.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cakeslist.data.models.Cake
import com.example.cakeslist.domain.usecases.GetCakesUseBaseCase
import com.example.cakeslist.domain.usecases.GetCakesUseCase
import com.example.cakeslist.presentation.BaseViewModel
import com.example.cakeslist.presentation.models.LoadingState
import com.example.cakeslist.utils.DispatcherFactory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel (
    private val dispatcherFactory: DispatcherFactory,
    private val getCakesUseCase: GetCakesUseCase
) : BaseViewModel(dispatcherFactory) {

    companion object {
        const val LOAD_ERROR = "load_error"
    }

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    private val _cakesList = MutableLiveData<List<Cake>>()
    val cakesList: LiveData<List<Cake>>
        get() = _cakesList


    fun getCakes() {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCakesUseCase(Unit).onStart { _loadingState.postValue(LoadingState.LOADING) }
                    .catch {
                        _loadingState.postValue(
                            LoadingState.error(LOAD_ERROR)
                        )
                    }
                    .collect {
                        _cakesList.postValue(it)
                    }
            }
        }
    }
}