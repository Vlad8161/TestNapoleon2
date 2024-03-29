package ru.napoleonit.testnapoleon2.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import java.util.regex.Pattern


fun View.dip(dp: Int): Int = (resources.displayMetrics.density * dp).toInt()
fun Context.dip(dp: Int): Int = (resources.displayMetrics.density * dp).toInt()

var View.visible: Boolean
    get() = when (visibility) {
        View.VISIBLE -> true
        else -> false
    }
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.applyToolbarElevation() {
    ViewCompat.setElevation(this, dip(4).toFloat())
}

fun View.applyButtonElevation() {
    ViewCompat.setElevation(this, dip(2).toFloat())
}

fun String.validatePassword(): Boolean {
    if (length < 6) {
        return false
    }

    if (!any { it.isUpperCase() }) {
        return false
    }

    if (!any { it.isLowerCase() }) {
        return false
    }

    if (!any { it.isDigit() }) {
        return false
    }

    return true
}

fun String.validateEmail(): Boolean {
    val (name, host) = split("@").takeIf { it.size == 2 }
        ?.let { it[0] to it[1] }
        ?: return false

    if (name.any { !it.isDigit() && !it.isLowerCase() && it != '.' && it != '_' }) {
        return false
    }

    if (host.any { !it.isDigit() && !it.isLowerCase() && it != '.' }) {
        return false
    }

    val hostParts = host.split(".")

    if (hostParts.size < 2) {
        return false
    }

    for (i in 1 until hostParts.size) {
        if (hostParts[i].length < 2) {
            return false
        }
    }

    return true
}

fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}

inline fun <reified T : Activity> Activity.toScreen() {
    startActivity(Intent(this, T::class.java))
}