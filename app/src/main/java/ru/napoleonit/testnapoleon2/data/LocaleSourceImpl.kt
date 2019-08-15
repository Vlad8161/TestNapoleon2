package ru.napoleonit.testnapoleon2.data

import android.content.Context
import android.os.Build
import java.util.*
import javax.inject.Inject


class LocaleSourceImpl @Inject constructor(
    private val context: Context
) : LocaleSource {
    override val currentLocaleStr: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0].language
        } else {
            context.resources.configuration.locale.language
        }.let {
            if (Locale("ru").language == it) {
                "ru"
            } else {
                "en"
            }
        }
}