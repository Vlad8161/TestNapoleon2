package ru.napoleonit.testnapoleon2

import org.junit.Test

import org.junit.Assert.*
import ru.napoleonit.testnapoleon2.view.validateEmail

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmailValidationUnitTest {
    @Test
    fun emailValidation() {
        assertTrue("test@gmail.com".validateEmail())
        assertTrue("test@gmail.com.ru".validateEmail())
        assertTrue("test.0_@gmail.com.ru".validateEmail())
        assertTrue("test@g.com.ru".validateEmail())
        assertFalse("tesT.0_@gmail.com.ru".validateEmail())
        assertFalse("test@gmail.c.ru".validateEmail())
        assertFalse("test@g.com.ru@g.com.ru".validateEmail())
        assertFalse("test@g.co_m.ru".validateEmail())
        assertFalse("test@g.coMm.ru".validateEmail())
        assertFalse("".validateEmail())
    }
}
