package com.example.notehub.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import java.io.File

@Composable
fun EditorScreen() {
    val file = File(FileUtils.ROOT_PATH.addPath("README.md"))
    var textState by remember { mutableStateOf(file.readText()) }
    Column {
        Text("Edit Markdown file:")
        MarkdownEditor(
            initialText = textState,
            onTextChanged = { newText ->
                textState = newText
                file.writeText(newText)
            }
        )
    }
}

@Composable
fun MarkdownEditor(initialText: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = initialText,
        onValueChange = onTextChanged,
        modifier = Modifier.fillMaxWidth()
    )
}