package ru.napoleonit.testnapoleon2.presentation.create

import android.os.Bundle
import ru.napoleonit.testnapoleon2.R
import ru.napoleonit.testnapoleon2.base.BaseActivity


class CreateActivity : BaseActivity<CreateViewModel>() {
    override val viewModelClass: Class<CreateViewModel>
        get() = CreateViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }
}