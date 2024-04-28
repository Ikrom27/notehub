package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.colintheshots.twain.MarkdownEditor
import com.colintheshots.twain.MarkdownText
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.ui.bars.EditorBar
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import java.io.File

@SuppressLint("ResourceType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorScreen() {
    var isEditableMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            EditorBar(
                title = "My note",
                isEditMode = isEditableMode
            ) {
                isEditableMode = !isEditableMode
            }
        },

    ) {
        LazyColumn(
            modifier = Modifier
                .background(Color.Black)
                .padding(top = it.calculateTopPadding())
        ) {
            item {
                DisplayMarkDown(isEditableMode)
            }
        }
    }
}

@Composable
fun DisplayMarkDown(
    isEditableMode: Boolean
){
    val file = File(FileUtils.ROOT_PATH.addPath("README.md"))
    var textFieldValue by remember { mutableStateOf(TextFieldValue(file.readText())) }
    var textColor = MaterialTheme.colorScheme.onBackground
    if (isEditableMode) {
        MarkdownEditor(
            value = textFieldValue.text,
            onValueChange = {value ->
                textFieldValue = TextFieldValue(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
            setView = {
                it.setTextColor(textColor.toArgb())
                it.setTextSize(18f)
            }
        )
    } else {
        MarkdownText(
            markdown = textFieldValue.text,
            color = textColor,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
        )
    }
}