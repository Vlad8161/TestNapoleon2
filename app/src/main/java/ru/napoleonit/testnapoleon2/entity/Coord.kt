package ru.napoleonit.testnapoleon2.entity

import com.squareup.moshi.Json

data class Coord(

	@Json(name="lon")
	val lon: Double? = null,

	@Json(name="lat")
	val lat: Double? = null
)