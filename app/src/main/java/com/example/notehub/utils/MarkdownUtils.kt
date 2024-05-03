package com.example.notehub.utils

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object MarkdownUtils {
    private const val TAG = "MarkdownUtils"
    private const val ITALIC_MARKER = "_"
    private const val BOLD_MARKER = "**"
    private const val STRIKETHROUGH_MARKER = "~~"
    private const val CODE_MARKER = "`"

    fun toggleBold(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeBold(value) else makeBold(value)
    }

    fun toggleItalic(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeItalic(value) else makeItalic(value)
    }

    fun toggleStrikethrough(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeStrikethrough(value) else makeStrikethrough(value)
    }

    fun toggleCode(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeCode(value) else makeCode(value)
    }

    fun makeItalic(value: TextFieldValue): TextFieldValue {
        return formatTextWith(ITALIC_MARKER, value)
    }

    fun makeBold(value: TextFieldValue): TextFieldValue{
        return formatTextWith(BOLD_MARKER, value)
    }

    fun makeStrikethrough(value: TextFieldValue): TextFieldValue {
        return formatTextWith(STRIKETHROUGH_MARKER, value)
    }

    fun makeCode(value: TextFieldValue): TextFieldValue {
        return formatTextWith(CODE_MARKER, value)
    }

    fun removeBold(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(BOLD_MARKER, value)
    }

    fun removeItalic(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(ITALIC_MARKER, value)
    }

    fun removeStrikethrough(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(STRIKETHROUGH_MARKER, value)
    }

    fun removeCode(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(CODE_MARKER, value)
    }

    fun isBold(value: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(value)
        return isMarked(value.text.substring(start, end), BOLD_MARKER)
    }

    fun isItalic(value: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(value)
        return isMarked(value.text.substring(start, end), ITALIC_MARKER)
    }

    fun isStrikethrough(value: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(value)
        return isMarked(value.text.substring(start, end), STRIKETHROUGH_MARKER)
    }

    fun isCode(value: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(value)
        return isMarked(value.text.substring(start, end), CODE_MARKER)
    }

    private fun isMarked(text: String, marker: String): Boolean {
        val regex = Regex("(?<=\\Q$marker\\E)(?=\\S).*\\S(?<=\\S)(?=\\Q$marker\\E)")
        return regex.containsMatchIn(text)
    }

    private fun unFormatTextFrom(marker: String, value: TextFieldValue): TextFieldValue{
        val text = value.text
        var (start, end) = getSelectedIndices(value)
        val newText = text.substring(0, start) +
                unMarkText(text.substring(start, end), marker) +
                text.substring(end, text.length)
        val newSelection = TextRange(value.selection.start-marker.length, value.selection.end-marker.length)
        return value.copy(newText, newSelection)
    }

    private fun formatTextWith(marker: String, value: TextFieldValue): TextFieldValue {
        val (start, end) = getSelectedIndices(value)
        val newText = markText(marker, value.text, start, end)
        val newSelection = TextRange(value.selection.start+marker.length, value.selection.end+marker.length)
        return value.copy(newText, newSelection)
    }

    private fun getSelectedIndices(value: TextFieldValue): Pair<Int, Int>{
        val text = value.text
        var start = value.selection.start
        var end = value.selection.end
        if (start == end){
            start = findWordStart(start, text)
            end = findWordEnd(end, text)
        }
        return Pair(start, end)
    }

    private fun findWordStart(index: Int, text: String): Int {
        var start = index
        while (start > 0 && isCorrect(text[start-1])){
            start--
        }
        return start
    }

    private fun findWordEnd(index: Int, text: String): Int{
        var end = index
        while (end < text.length && isCorrect(text[end])){
            end++
        }
        return end
    }

    private fun isCorrect(char: Char): Boolean{
        return char !in setOf(',', '.', ' ', '!', ';', ':', '\n')
    }

    private fun unMarkText(text: String, marker: String): String {
        var start = 0
        var end = text.length
        while (text.substring(start, start+marker.length) != marker){
            start++
        }
        while (text.substring(end-marker.length, end) != marker){
            end--
        }
        return text.substring(0, start) + text.substring(start+marker.length, end-marker.length) + text.substring(end, text.length)
    }

    private fun markText(marker: String, text: String, start: Int, end: Int): String{
        return text.substring(0, start) + marker + text.substring(start, end) + marker + text.substring(end, text.length)
    }
}
