package ru.napoleonit.testnapoleon2.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass


@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)