package com.example.notehub.utils

import androidx.compose.ui.text.input.TextFieldValue

object MarkdownUtils {
    private const val TAG = "MarkdownUtils"
    private const val ITALIC_MARKER = "_"
    private const val BOLD_MARKER = "**"
    private const val STRIKETHROUGH_MARKER = "~~"
    private const val CODE_MARKER = "`"

    fun toggleBold(textFieldValue: TextFieldValue, selected: Boolean): String {
        return if (selected) removeBold(textFieldValue) else makeBold(textFieldValue)
    }

    fun toggleItalic(textFieldValue: TextFieldValue, selected: Boolean): String {
        return if (selected) removeItalic(textFieldValue) else makeItalic(textFieldValue)
    }

    fun toggleStrikethrough(textFieldValue: TextFieldValue, selected: Boolean): String {
        return if (selected) removeStrikethrough(textFieldValue) else makeStrikethrough(textFieldValue)
    }

    fun toggleCode(textFieldValue: TextFieldValue, selected: Boolean): String {
        return if (selected) removeCode(textFieldValue) else makeCode(textFieldValue)
    }


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

    fun removeBold(textFieldValue: TextFieldValue,): String {
        return unFormatTextFrom(BOLD_MARKER, textFieldValue)
    }

    fun removeItalic(textFieldValue: TextFieldValue,): String {
        return unFormatTextFrom(ITALIC_MARKER, textFieldValue)
    }

    fun removeStrikethrough(textFieldValue: TextFieldValue,): String {
        return unFormatTextFrom(STRIKETHROUGH_MARKER, textFieldValue)
    }

    fun removeCode(textFieldValue: TextFieldValue,): String {
        return unFormatTextFrom(CODE_MARKER, textFieldValue)
    }

    fun isBold(textFieldValue: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(textFieldValue)
        return isMarked(textFieldValue.text.substring(start, end), BOLD_MARKER)
    }

    fun isItalic(textFieldValue: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(textFieldValue)
        return isMarked(textFieldValue.text.substring(start, end), ITALIC_MARKER)
    }

    fun isStrikethrough(textFieldValue: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(textFieldValue)
        return isMarked(textFieldValue.text.substring(start, end), STRIKETHROUGH_MARKER)
    }

    fun isCode(textFieldValue: TextFieldValue): Boolean{
        val (start, end) = getSelectedIndices(textFieldValue)
        return isMarked(textFieldValue.text.substring(start, end), CODE_MARKER)
    }

    private fun isMarked(text: String, marker: String): Boolean {
        val regex = Regex("(?<=\\Q$marker\\E)(?=\\S).*\\S(?<=\\S)(?=\\Q$marker\\E)")
        return regex.containsMatchIn(text)
    }

    private fun unFormatTextFrom(marker: String, textFieldValue: TextFieldValue): String{
        val text = textFieldValue.text
        var (start, end) = getSelectedIndices(textFieldValue)
        return text.substring(0, start) +
                unMarkText(text.substring(start, end), marker) +
                text.substring(end, text.length)
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

    private fun markText(mark: String, text: String, start: Int, end: Int): String{
        return text.substring(0, start) + mark + text.substring(start, end) + mark + text.substring(end, text.length)
    }
}