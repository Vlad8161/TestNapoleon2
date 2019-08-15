package ru.napoleonit.testnapoleon2.entity

import com.squareup.moshi.Json

data class Clouds(

	@Json(name="all")
	val all: Int? = null
)