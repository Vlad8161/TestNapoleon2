package ru.napoleonit.testnapoleon2.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class LocationSource(
    context: Context
) : LifecycleObserver {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationService: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var locationListener: LocationListener? = null
    private var timeoutRunnable: Runnable? = null
    private val uiHandler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingPermission")
    fun getLocation(callback: (Location?) -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cancelListener()
            cancelTimeout()
            val newLocationListener = SourceLocationListener(callback).also {
                locationListener = it
            }
            val newTimeoutRunnable = Runnable {
                cancelListener()
                callback(null)
            }.also {
                timeoutRunnable = it
            }
            locationService.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                newLocationListener,
                Looper.getMainLooper()
            )
            locationService.requestSingleUpdate(
                LocationManager.NETWORK_PROVIDER,
                newLocationListener,
                Looper.getMainLooper()
            )
            uiHandler.postDelayed(newTimeoutRunnable,
                LOCATION_TIMEOUT
            )
        } else {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                callback(it.result)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        cancelListener()
        cancelTimeout()
    }

    private fun cancelListener() {
        locationListener?.let { locationService.removeUpdates(it) }
        locationListener = null
    }

    private fun cancelTimeout() {
        uiHandler.removeCallbacks(timeoutRunnable)
    }

    inner class SourceLocationListener(
        private val callback: (Location?) -> Unit
    ) : LocationListener {
        override fun onLocationChanged(location: Location?) {
            callback(location)
            locationService.removeUpdates(this)
            cancelTimeout()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    companion object {
        const val LOCATION_TIMEOUT = 5000L
    }
}
