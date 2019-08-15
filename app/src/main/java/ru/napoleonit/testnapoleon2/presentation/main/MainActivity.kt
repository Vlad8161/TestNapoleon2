package ru.napoleonit.testnapoleon2.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.napoleonit.testnapoleon2.App
import ru.napoleonit.testnapoleon2.R
import ru.napoleonit.testnapoleon2.base.BaseActivity
import ru.napoleonit.testnapoleon2.interactor.UiWeather
import ru.napoleonit.testnapoleon2.view.*


class MainActivity : BaseActivity<MainViewModel>() {
    private lateinit var locationSource: LocationSource

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        locationSource = LocationSource(this)

        setContentView(R.layout.activity_main)

        activityMainToolbar.apply {
            applyToolbarElevation()
            setSupportActionBar(this)
        }

        activityMainBtnBack.setOnClickListener {
            onBackPressed()
        }

        activityMainBtnCreate.setOnClickListener {
            Toast.makeText(this, R.string.activity_main_unimplemented_message, Toast.LENGTH_SHORT)
                .show()
        }

        activityMainBtnPasswordHelp.setOnClickListener {
            Toast.makeText(this, R.string.activity_main_password_help, Toast.LENGTH_LONG)
                .show()
        }

        activityMainBtnLogin.apply {
            applyButtonElevation()
            setOnClickListener { onSubmitClick() }
        }

        activityMainPassword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                onSubmitClick()
                return@OnKeyListener true
            }
            false
        })

        viewModel.weatherMsg.observe(this, Observer { event ->
            event.unhandledValue?.let { msg -> handleMessage(msg) }
        })

        viewModel.progressVisibility.observe(this, Observer { visible ->
            activityMainProgress.visible = visible
        })
    }

    @SuppressLint("MissingPermission")
    private fun onSubmitClick() {
        var errorDetected = false

        val email = activityMainEmail.text.toString()
        if (!email.validateEmail()) {
            activityMainEmailLayout.error = getString(R.string.activity_main_wrong_email)
            errorDetected = true
        } else {
            activityMainEmailLayout.error = null
        }

        val password = activityMainPassword.text.toString()
        if (!password.validatePassword()) {
            activityMainPasswordLayout.error =
                getString(R.string.activity_main_wrong_password)
            errorDetected = true
        } else {
            activityMainPasswordLayout.error = null
        }

        if (errorDetected) {
            return
        }

        hideKeyboard()

        viewModel.loginClick()
        withPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            onSuccessPermRequest = {
                locationSource.getLocation { location ->
                    if (location != null) {
                        viewModel.getWeather(location.latitude, location.longitude)
                    } else {
                        viewModel.failedToDetermineLocation()
                    }
                }
            }, onFailedPermRequest = {
                viewModel.locationPermissionWasNotGranted()
            })
    }

    private fun handleMessage(msg: Message) = when (msg) {
        is Message.Location -> {
            Snackbar.make(activityMainRoot, msg.uiWeather.toUserString(), Snackbar.LENGTH_LONG)
                .show()
        }
        Message.ErrorGetLocation -> {
            Snackbar.make(
                activityMainRoot,
                R.string.activity_main_location_error,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        Message.PermissionNotGranted -> {
            Snackbar.make(
                activityMainRoot,
                R.string.activity_main_permission_error,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        Message.NetworkError -> {
            Snackbar.make(
                activityMainRoot,
                R.string.activity_main_network_error,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun UiWeather.toUserString(): String = mutableListOf<String>().apply {
        if (cityName != null) {
            add(getString(R.string.activity_main_city_template, cityName))
        }
        if (description != null) {
            add(getString(R.string.activity_main_weather_template, description))
        }
        if (temperature != null) {
            add(getString(R.string.activity_main_temperature_template, temperature))
        }
        if (windSpeed != null) {
            add(getString(R.string.activity_main_wind_speed_template, windSpeed))
        }
    }.joinToString(", ")
}

