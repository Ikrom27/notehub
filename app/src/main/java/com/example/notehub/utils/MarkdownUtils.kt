package com.example.notehub.utils

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Utility class for handling Markdown formatting operations on text fields.
 */
object MarkdownUtils {

    /** Tag used for logging within the MarkdownUtils class. */
    private const val TAG = "MarkdownUtils"

    /** Marker for italic text in Markdown. */
    private const val ITALIC_MARKER = "_"

    /** Marker for bold text in Markdown. */
    private const val BOLD_MARKER = "**"

    /** Marker for strikethrough text in Markdown. */
    private const val STRIKETHROUGH_MARKER = "~~"

    /** Marker for code text in Markdown. */
    private const val CODE_MARKER = "`"

    /**
     * Toggles the bold formatting of the selected text within the provided TextFieldValue.
     * If selected is true, removes bold formatting; otherwise, applies bold formatting.
     *
     * @param value The TextFieldValue containing the text to toggle bold formatting for.
     * @param selected Boolean flag indicating whether the text is currently bold formatted.
     * @return The updated TextFieldValue with the appropriate bold formatting changes.
     */
    fun toggleBold(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeBold(value) else makeBold(value)
    }

    /**
     * Toggles the italic formatting of the selected text within the provided TextFieldValue.
     * If selected is true, removes italic formatting; otherwise, applies italic formatting.
     *
     * @param value The TextFieldValue containing the text to toggle italic formatting for.
     * @param selected Boolean flag indicating whether the text is currently italic formatted.
     * @return The updated TextFieldValue with the appropriate italic formatting changes.
     */
    fun toggleItalic(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeItalic(value) else makeItalic(value)
    }

    /**
     * Toggles the strikethrough formatting of the selected text within the provided TextFieldValue.
     * If selected is true, removes strikethrough formatting; otherwise, applies strikethrough formatting.
     *
     * @param value The TextFieldValue containing the text to toggle strikethrough formatting for.
     * @param selected Boolean flag indicating whether the text is currently strikethrough formatted.
     * @return The updated TextFieldValue with the appropriate strikethrough formatting changes.
     */
    fun toggleStrikethrough(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeStrikethrough(value) else makeStrikethrough(value)
    }

    /**
     * Toggles the code formatting of the selected text within the provided TextFieldValue.
     * If selected is true, removes code formatting; otherwise, applies code formatting.
     *
     * @param value The TextFieldValue containing the text to toggle code formatting for.
     * @param selected Boolean flag indicating whether the text is currently code formatted.
     * @return The updated TextFieldValue with the appropriate code formatting changes.
     */
    fun toggleCode(value: TextFieldValue, selected: Boolean): TextFieldValue {
        return if (selected) removeCode(value) else makeCode(value)
    }

    /**
     * Applies italic formatting to the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to format.
     * @return The updated TextFieldValue with italic formatting applied.
     */
    fun makeItalic(value: TextFieldValue): TextFieldValue {
        return formatTextWith(ITALIC_MARKER, value)
    }

    /**
     * Applies bold formatting to the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to format.
     * @return The updated TextFieldValue with bold formatting applied.
     */
    fun makeBold(value: TextFieldValue): TextFieldValue {
        return formatTextWith(BOLD_MARKER, value)
    }

    /**
     * Applies strikethrough formatting to the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to format.
     * @return The updated TextFieldValue with strikethrough formatting applied.
     */
    fun makeStrikethrough(value: TextFieldValue): TextFieldValue {
        return formatTextWith(STRIKETHROUGH_MARKER, value)
    }

    /**
     * Applies code formatting to the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to format.
     * @return The updated TextFieldValue with code formatting applied.
     */
    fun makeCode(value: TextFieldValue): TextFieldValue {
        return formatTextWith(CODE_MARKER, value)
    }

    fun makeNumbered(value: TextFieldValue): TextFieldValue {
        val text = value.text
        val currentStart = getLineStartsIndex(value, '\n').first
        var prevStart = getLineStartsIndex(value.copy(selection = TextRange(currentStart-2)), '\n').first
        var spaceCount = 0
        var lastNumber = 0
        while (!text[prevStart].isDigit()){
            spaceCount++
            prevStart++
        }
        while (text[prevStart].isDigit()) {
            lastNumber = lastNumber*10 + text[prevStart].digitToInt()
            prevStart++
        }
        val newText = text.substring(0, currentStart) + " ".repeat(spaceCount) + (lastNumber+1) + ". " + text.substring(currentStart, text.length)
        val newSelection = TextRange(value.selection.start+lastNumber.toString().length)
        return TextFieldValue(newText, newSelection)
    }

    /**
     * Removes bold formatting from the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to remove bold formatting from.
     * @return The updated TextFieldValue with bold formatting removed.
     */
    fun removeBold(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(BOLD_MARKER, value)
    }

    /**
     * Removes italic formatting from the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to remove italic formatting from.
     * @return The updated TextFieldValue with italic formatting removed.
     */
    fun removeItalic(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(ITALIC_MARKER, value)
    }

    /**
     * Removes strikethrough formatting from the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to remove strikethrough formatting from.
     * @return The updated TextFieldValue with strikethrough formatting removed.
     */
    fun removeStrikethrough(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(STRIKETHROUGH_MARKER, value)
    }

    /**
     * Removes code formatting from the selected text within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text to remove code formatting from.
     * @return The updated TextFieldValue with code formatting removed.
     */
    fun removeCode(value: TextFieldValue): TextFieldValue {
        return unFormatTextFrom(CODE_MARKER, value)
    }

    /**
     * Checks if the selected text within the provided TextFieldValue is bold formatted.
     *
     * @param value The TextFieldValue containing the text to check.
     * @return True if the selected text is bold formatted, false otherwise.
     */
    fun isBold(value: TextFieldValue): Boolean {
        val (start, end) = getSelectedIndices(value)
        return isWordMarked(value.text.substring(start, end), BOLD_MARKER)
    }

    /**
     * Checks if the selected text within the provided TextFieldValue is italic formatted.
     *
     * @param value The TextFieldValue containing the text to check.
     * @return True if the selected text is italic formatted, false otherwise.
     */
    fun isItalic(value: TextFieldValue): Boolean {
        val (start, end) = getSelectedIndices(value)
        return isWordMarked(value.text.substring(start, end), ITALIC_MARKER)
    }

    /**
     * Checks if the selected text within the provided TextFieldValue is strikethrough formatted.
     *
     * @param value The TextFieldValue containing the text to check.
     * @return True if the selected text is strikethrough formatted, false otherwise.
     */
    fun isStrikethrough(value: TextFieldValue): Boolean {
        val (start, end) = getSelectedIndices(value)
        return isWordMarked(value.text.substring(start, end), STRIKETHROUGH_MARKER)
    }

    /**
     * Checks if the selected text within the provided TextFieldValue is code formatted.
     *
     * @param value The TextFieldValue containing the text to check.
     * @return True if the selected text is code formatted, false otherwise.
     */
    fun isCode(value: TextFieldValue): Boolean {
        val (start, end) = getSelectedIndices(value)
        return isWordMarked(value.text.substring(start, end), CODE_MARKER)
    }

    fun isNumbered(value: TextFieldValue): Boolean{
        val (start, end) = getLineStartsIndex(value, '\n')
        val regex = Regex("\\s*\\d+\\.\\s")
        return regex.containsMatchIn(value.text.substring(start, end))
    }

    /**
     * Internal function to check if a character is correct for Markdown formatting.
     *
     * @param char The character to check.
     * @return True if the character is correct, false otherwise.
     */
    private fun isCorrect(char: Char): Boolean {
        return char !in setOf(',', '.', ' ', '!', ';', ':', '\n')
    }

    /**
     * Internal function to find the starting index of a word within the provided text.
     *
     * @param index The index to start the search from.
     * @param text The text to search within.
     * @return The index of the start of the word.
     */
    private fun findWordStart(index: Int, text: String): Int {
        var start = index
        while (start > 0 && isCorrect(text[start - 1])) {
            start--
        }
        return start
    }

    /**
     * Internal function to find the ending index of a word within the provided text.
     *
     * @param index The index to start the search from.
     * @param text The text to search within.
     * @return The index of the end of the word.
     */
    private fun findWordEnd(index: Int, text: String): Int {
        var end = index
        while (end < text.length && isCorrect(text[end])) {
            end++
        }
        return end
    }

    /**
     * Internal function to mark the text with the specified marker.
     *
     * @param marker The marker to apply.
     * @param text The text to format.
     * @param start The start index of the selection.
     * @param end The end index of the selection.
     * @return The formatted text.
     */
    private fun markText(marker: String, text: String, start: Int, end: Int): String {
        return text.substring(0, start) + marker + text.substring(start, end) + marker + text.substring(end, text.length)
    }

    /**
     * Internal function to unformat the text from the specified marker.
     *
     * @param marker The marker to remove.
     * @param value The TextFieldValue containing the text to unformat.
     * @return The unformatted TextFieldValue.
     */
    private fun unFormatTextFrom(marker: String, value: TextFieldValue): TextFieldValue {
        val text = value.text
        var (start, end) = getSelectedIndices(value)
        val newText = text.substring(0, start) +
                unMarkText(text.substring(start, end), marker) +
                text.substring(end, text.length)
        val newSelection = TextRange(value.selection.start - marker.length, value.selection.end - marker.length)
        return value.copy(newText, newSelection)
    }

    /**
     * Internal function to format the text with the specified marker.
     *
     * @param marker The marker to apply.
     * @param value The TextFieldValue containing the text to format.
     * @return The formatted TextFieldValue.
     */
    private fun formatTextWith(marker: String, value: TextFieldValue): TextFieldValue {
        val (start, end) = getSelectedIndices(value)
        val newText = markText(marker, value.text, start, end)
        val newSelection = TextRange(value.selection.start + marker.length, value.selection.end + marker.length)
        return value.copy(newText, newSelection)
    }

    /**
     * Internal function to retrieve the selected indices within the provided TextFieldValue.
     *
     * @param value The TextFieldValue containing the text.
     * @return A Pair representing the start and end indices of the selection.
     */
    private fun getSelectedIndices(value: TextFieldValue): Pair<Int, Int> {
        val text = value.text
        var start = value.selection.start
        var end = value.selection.end
        if (start == end) {
            start = findWordStart(start, text)
            end = findWordEnd(end, text)
        }
        return Pair(start, end)
    }

    private fun getLineStartsIndex(value: TextFieldValue, endMarker: Char): Pair<Int, Int>{
        val text = value.text
        var start = value.selection.start
        while (start >= 0 && text[start] != '\n'){
            start--
        }
        if (text[start] == '\n'){
            start++
        }
        var end = start
        while (end < text.length && text[end] != endMarker){
            end++
        }
        if (end+1 < text.length){
            end++
        }
        return Pair(start, end)
    }

    /**
     * Internal function to unmark the text from the specified marker.
     *
     * @param text The text to unformat.
     * @param marker The marker to remove.
     * @return The unformatted text.
     */
    private fun unMarkText(text: String, marker: String): String {
        var start = 0
        var end = text.length
        while (text.substring(start, start + marker.length) != marker) {
            start++
        }
        while (text.substring(end - marker.length, end) != marker) {
            end--
        }
        return text.substring(0, start) + text.substring(start + marker.length, end - marker.length) + text.substring(end, text.length)
    }

    /**
     * Internal function to check if a portion of text is marked with the specified marker.
     *
     * @param text The text to check.
     * @param marker The marker to look for.
     * @return True if the text is marked, false otherwise.
     */
    private fun isWordMarked(text: String, marker: String): Boolean {
        val regex = Regex("(?<=\\Q$marker\\E)(?=\\S).*\\S(?<=\\S)(?=\\Q$marker\\E)")
        return regex.containsMatchIn(text)
    }
}
