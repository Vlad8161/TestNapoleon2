package ru.napoleonit.testnapoleon2.entity

import com.squareup.moshi.Json

data class Main(

	@Json(name="temp")
	val temp: Double? = null,

	@Json(name="temp_min")
	val tempMin: Double? = null,

	@Json(name="humidity")
	val humidity: Double? = null,

	@Json(name="pressure")
	val pressure: Double? = null,

	@Json(name="temp_max")
	val tempMax: Double? = null
)