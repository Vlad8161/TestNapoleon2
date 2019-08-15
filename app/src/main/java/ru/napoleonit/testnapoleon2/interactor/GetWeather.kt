package ru.napoleonit.testnapoleon2.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.napoleonit.testnapoleon2.base.toCelcius
import ru.napoleonit.testnapoleon2.data.LocaleSource
import ru.napoleonit.testnapoleon2.data.NetworkConfigSource
import ru.napoleonit.testnapoleon2.data.WeatherApi
import javax.inject.Inject


class GetWeather @Inject constructor(
    private val weatherApi: WeatherApi,
    private val networkConfigSource: NetworkConfigSource,
    private val localeSource: LocaleSource
) {
    suspend operator fun invoke(lat: Double, lng: Double) = withContext(Dispatchers.IO) {
        val weatherResponse = weatherApi.getWeather(
            appId = networkConfigSource.apiKey,
            lat = lat,
            lon = lng,
            lang = localeSource.currentLocaleStr
        )
        UiWeather(
            cityName = weatherResponse.name,
            temperature = weatherResponse.main?.temp?.toCelcius(),
            description = weatherResponse.weather?.firstOrNull()?.description,
            windSpeed = weatherResponse.wind?.speed
        )
    }
}

class UiWeather(
    val cityName: String?,
    val temperature: Double?,
    val description: String?,
    val windSpeed: Double?
)