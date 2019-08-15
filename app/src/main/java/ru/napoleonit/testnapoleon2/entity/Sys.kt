package ru.napoleonit.testnapoleon2.entity

import com.squareup.moshi.Json

data class Sys(

	@Json(name="country")
	val country: String? = null,

	@Json(name="sunrise")
	val sunrise: Int? = null,

	@Json(name="sunset")
	val sunset: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="type")
	val type: Int? = null,

	@Json(name="message")
	val message: Double? = null
)