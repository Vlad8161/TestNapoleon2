package ru.napoleonit.testnapoleon2.data

import ru.napoleonit.testnapoleon2.BuildConfig
import javax.inject.Inject


class NetworkConfigSourceImpl @Inject constructor(): NetworkConfigSource {
    override val apiKey: String
        get() = BuildConfig.WEATHER_API_KEY
    override val baseUrl: String
        get() = BuildConfig.BASE_URL
}