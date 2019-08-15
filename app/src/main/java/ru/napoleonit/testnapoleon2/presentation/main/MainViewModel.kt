package ru.napoleonit.testnapoleon2.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.napoleonit.testnapoleon2.base.BaseViewModel
import ru.napoleonit.testnapoleon2.base.Event
import ru.napoleonit.testnapoleon2.interactor.GetWeather
import ru.napoleonit.testnapoleon2.interactor.UiWeather
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val getWeatherInteractor: GetWeather
) : BaseViewModel() {
    private var job: Job? = null

    private val mWeatherMsg = MutableLiveData<Event<Message>>()
    val weatherMsg: LiveData<Event<Message>>
        get() = mWeatherMsg

    private val mProgressVisibility = MutableLiveData<Boolean>().apply { value = false }
    val progressVisibility: LiveData<Boolean>
        get() = mProgressVisibility

    fun loginClick() {
        mProgressVisibility.value = true
    }

    fun getWeather(lat: Double, lng: Double) {
        if (job?.isActive != true) {
            job = scope.launch {
                try {
                    mProgressVisibility.value = true
                    mWeatherMsg.value = Event(Message.Location(getWeatherInteractor(lat, lng)))
                } catch (e: Exception) {
                    mWeatherMsg.value = Event(Message.NetworkError)
                } finally {
                    mProgressVisibility.value = false
                }
            }
        }
    }

    fun locationPermissionWasNotGranted() {
        mWeatherMsg.value = Event(Message.PermissionNotGranted)
        mProgressVisibility.value = false
    }

    fun failedToDetermineLocation() {
        mWeatherMsg.value = Event(Message.ErrorGetLocation)
        mProgressVisibility.value = false
    }
}

sealed class Message {
    class Location(val uiWeather: UiWeather) : Message()
    object PermissionNotGranted : Message()
    object ErrorGetLocation : Message()
    object NetworkError : Message()
}
