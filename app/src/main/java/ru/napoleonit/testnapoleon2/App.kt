package ru.napoleonit.testnapoleon2

import android.app.Application
import ru.napoleonit.testnapoleon2.di.AppComponent
import ru.napoleonit.testnapoleon2.di.DaggerAppComponent


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}