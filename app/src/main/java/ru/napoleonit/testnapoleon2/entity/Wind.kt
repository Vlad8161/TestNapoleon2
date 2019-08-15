package ru.napoleonit.testnapoleon2.entity

import com.squareup.moshi.Json

data class Wind(

	@Json(name="deg")
	val deg: Double? = null,

	@Json(name="speed")
	val speed: Double? = null
)