package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.colintheshots.twain.MarkdownEditor
import com.colintheshots.twain.MarkdownText
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.TOP_BAR_HEIGHT
import com.example.notehub.ui.bars.EditorBar
import com.example.notehub.ui.components.EditPanel
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import java.io.File

@SuppressLint("ResourceType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorScreen() {
    val file = File(FileUtils.ROOT_PATH.addPath("README.md"))
    var isEditableMode by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(file.readText())) }

    Box {
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
//                    DisplayMarkDown(isEditableMode, textFieldValue.text) {
//                        textFieldValue = TextFieldValue(
//                            text = it,
//                            selection = TextRange(textFieldValue.selection.start, textFieldValue.selection.end)
//                        )
//                        Log.d("Editor", "Selection ${textFieldValue.selection.start}")
//                    }
                    TextField(value = textFieldValue, onValueChange = {
                        textFieldValue = it
                        Log.d("Editor", "Selection ${textFieldValue.selection.start}")
                    })
                }
            }
        }
        EditPanel(
            modifier = Modifier.align(Alignment.BottomCenter),
            textFieldValue
        ) {
            textFieldValue = TextFieldValue(
                text = it,
                selection = TextRange(textFieldValue.selection.start, textFieldValue.selection.end)
            )
        }
    }
}

@Composable
fun DisplayMarkDown(
    isEditableMode: Boolean,
    text: String,
    onTextChange: (String) -> Unit
){
    var textColor = MaterialTheme.colorScheme.onBackground
    if (isEditableMode) {
        MarkdownEditor(
            value = text,
            onValueChange = {value ->
                onTextChange(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
            setView = {
                it.setTextColor(textColor.toArgb())
                it.textSize = 18f
            }
        )
    } else {
        MarkdownText(
            markdown = text,
            color = textColor,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
        )
    }
}