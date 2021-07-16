package com.example.cakeslist.core.di

import com.example.cakeslist.data.CakeRepository
import com.example.cakeslist.domain.usecases.GetCakesUseBaseCase
import com.example.cakeslist.domain.usecases.GetCakesUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {
    single(named("get_cakes")) { provideGetCakes(get()) }
}

fun provideGetCakes(repository: CakeRepository): GetCakesUseBaseCase {
    return GetCakesUseCase(repository)
}