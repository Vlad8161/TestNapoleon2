package ru.napoleonit.testnapoleon2.base


class Event<T : Any>(
    private val mValue: T
) {
    private var handled: Boolean = false

    val unhandledValue: T?
        get() = if (!handled) value else null

    val value: T
        get() = mValue.also { handled = true }
}