package com.example.notehub.utils

import androidx.compose.ui.text.input.TextFieldValue

object MarkdownUtils {
    private const val TAG = "MarkdownUtils"
    private const val ITALIC_MARKER = "_"
    private const val BOLD_MARKER = "**"
    private const val STRIKETHROUGH_MARKER = "~~"
    private const val UNDERLINE_MARKER = "<sub>"
    private const val CODE_MARKER = "`"

    fun makeItalic(textFieldValue: TextFieldValue): String {
        return formatTextWith(ITALIC_MARKER, textFieldValue)
    }

    fun makeBold(textFieldValue: TextFieldValue): String{
        return formatTextWith(BOLD_MARKER, textFieldValue)
    }

    fun makeStrikethrough(textFieldValue: TextFieldValue): String {
        return formatTextWith(STRIKETHROUGH_MARKER, textFieldValue)
    }

    fun makeCode(textFieldValue: TextFieldValue): String {
        return formatTextWith(CODE_MARKER, textFieldValue)
    }

    fun makeUnderline(textFieldValue: TextFieldValue): String {
        return formatTextWith(UNDERLINE_MARKER, textFieldValue)
    }

    private fun formatTextWith(symbol: String, textFieldValue: TextFieldValue): String {
        val (start, end) = getSelectedIndices(textFieldValue)
        return markText(symbol, textFieldValue.text, start, end)
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

    private fun markText(symbol: String, text: String, start: Int, end: Int): String{
        return text.substring(0, start) + symbol + text.substring(start, end) + symbol + text.substring(end, text.length)
    }
}