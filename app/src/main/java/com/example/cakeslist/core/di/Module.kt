package com.example.cakeslist.core.di

import android.app.Application
import com.example.cakeslist.core.common.BASE_URL
import com.example.cakeslist.data.CakeRepository
import com.example.cakeslist.data.CakesApi
import com.example.cakeslist.utils.AppDispatcherFactory
import com.example.cakeslist.utils.DispatcherFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dispatcherFactoryModule = module {
    single<DispatcherFactory> {
        AppDispatcherFactory()
    }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }


}

val cakeApiModule = module {
    fun provideCakeApi(retrofit: Retrofit): CakesApi {
        return retrofit.create(CakesApi::class.java)
    }

    single { provideCakeApi(get()) }
}

val repositoryModule = module {
    single {
        CakeRepository(get())
    }
}