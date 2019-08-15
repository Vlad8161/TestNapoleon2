package ru.napoleonit.testnapoleon2.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.napoleonit.testnapoleon2.presentation.main.MainViewModel


@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainPresenter: MainViewModel): ViewModel
}