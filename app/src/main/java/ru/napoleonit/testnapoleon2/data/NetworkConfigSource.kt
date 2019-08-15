package ru.napoleonit.testnapoleon2.data


interface NetworkConfigSource {
    val baseUrl: String

    val apiKey: String
}