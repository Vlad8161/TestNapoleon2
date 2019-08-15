package ru.napoleonit.testnapoleon2.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.napoleonit.testnapoleon2.presentation.main.MainActivity
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ViewModelsModule::class,
        NetworkModule::class,
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}