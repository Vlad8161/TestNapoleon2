package ru.napoleonit.testnapoleon2.di

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.napoleonit.testnapoleon2.data.NetworkConfigSource
import ru.napoleonit.testnapoleon2.data.NetworkConfigSourceImpl
import ru.napoleonit.testnapoleon2.data.WeatherApi
import javax.inject.Singleton


@Module(includes = [NetworkModuleBindings::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherApi(
        networkConfigSource: NetworkConfigSource
    ) : WeatherApi = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(networkConfigSource.baseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    Log.d("LOGUSIKI", request.toString())
                    chain.proceed(request)
                }
                .build()
        )
        .build()
        .create(WeatherApi::class.java)
}

@Module
abstract class NetworkModuleBindings {

    @Singleton
    @Binds
    abstract fun bindNetworkConfig(networkConfigSourceImpl: NetworkConfigSourceImpl): NetworkConfigSource
}
