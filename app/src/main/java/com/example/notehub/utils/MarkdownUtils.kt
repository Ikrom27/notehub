package com.example.notehub.utils

import androidx.compose.ui.text.input.TextFieldValue

object MarkdownUtils {
    private const val TAG = "MarkdownUtils"

    fun setBold(textFieldValue: TextFieldValue): String{
        val (start, end) = getSelectedIndices(textFieldValue)
        return circleTextWith("**", textFieldValue.text, start, end)
    }

    private fun getSelectedIndices(textFieldValue: TextFieldValue): Pair<Int, Int>{
        val text = textFieldValue.text
        var start = textFieldValue.selection.start
        var end = textFieldValue.selection.end
        if (start == end){
            start = findWordStart(start, text)
            end = findWordEnd(end, text)
        }
        return Pair(start, end)
    }

    private fun findWordStart(index: Int, text: String): Int {
        var start = index
        while (text[start-1].isLetter() && start > 0){
            start--
        }
        return start
    }

    private fun findWordEnd(index: Int, text: String): Int{
        var end = index
        while (text[end].isLetter() && end < text.length){
            end++
        }
        return end
    }

    private fun circleTextWith(symbol: String, text: String, start: Int, end: Int): String{
        return text.substring(0, start) + "**" + text.substring(start, end) + "**" + text.substring(end, text.length)
    }
}