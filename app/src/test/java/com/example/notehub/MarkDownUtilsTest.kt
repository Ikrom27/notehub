package com.example.notehub

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notehub.utils.MarkdownUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MarkDownUtilsTest {
    @Test
    fun isNumberedTest1() {
        var text = "hello world \n1. I love you!"
        assertEquals(true,
            MarkdownUtils.isNumbered(TextFieldValue(text, TextRange(text.length-2)))
        )
    }

    @Test
    fun isNumberedTest2() {
        var text = "hello world \n1.I love you!"
        assertEquals(false,
            MarkdownUtils.isNumbered(TextFieldValue(text, TextRange(text.length-2)))
        )
    }

    @Test
    fun isNumberedTest3() {
        var text = "hello world \n21.  I love you!"
        assertEquals(true,
            MarkdownUtils.isNumbered(TextFieldValue(text, TextRange(text.length-2)))
        )
    }

    @Test
    fun isNumberedTest4() {
        var text = "hello world \n    21. I love you!"
        assertEquals(true,
            MarkdownUtils.isNumbered(TextFieldValue(text, TextRange(text.length-2)))
        )
    }

    @Test
    fun makeNumberedTest1() {
        val text = "hello world \n2. I love you!\n"
        val value = TextFieldValue(text, TextRange(text.length-1))
        val result = MarkdownUtils.makeNumbered(value).text
        assertEquals("hello world \n2. I love you!\n3. ",
            result
        )
    }
}