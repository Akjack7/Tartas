package com.example.cakeslist.core.di

import com.example.cakeslist.presentation.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            get(),
            getCakesUseCase = get(named("get_cakes"))
        )
    }
}
