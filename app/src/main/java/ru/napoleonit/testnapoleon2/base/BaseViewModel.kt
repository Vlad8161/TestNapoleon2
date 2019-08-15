package ru.napoleonit.testnapoleon2.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren


open class BaseViewModel : ViewModel() {
    protected val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}