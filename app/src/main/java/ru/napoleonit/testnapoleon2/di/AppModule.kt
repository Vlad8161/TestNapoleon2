package ru.napoleonit.testnapoleon2.di

import dagger.Binds
import dagger.Module
import ru.napoleonit.testnapoleon2.data.LocaleSource
import ru.napoleonit.testnapoleon2.data.LocaleSourceImpl
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindLocaleSource(localeSourceImpl: LocaleSourceImpl): LocaleSource
}