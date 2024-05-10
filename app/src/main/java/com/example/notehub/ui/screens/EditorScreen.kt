package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.ui.bars.EditorBar
import com.example.notehub.ui.components.EditPanel
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import com.ikrom.twain.MarkdownEditor
import com.ikrom.twain.MarkdownText
import java.io.File

@SuppressLint("ResourceType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorScreen(
    navController: NavController,
    dirName: String,
    fileName: String
) {
    val file = File(FileUtils.ROOT_PATH.addPath(dirName).addPath(fileName))
    var isEditableMode by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(file.readText()))
    }


    Box {
        Scaffold(
            topBar = {
                EditorBar(
                    title = fileName,
                    isEditMode = isEditableMode
                ) {
                    isEditableMode = !isEditableMode
                }
            },

            ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
            ) {
                item {
                    DisplayMarkDown(isEditableMode, textFieldValue) {
                        Log.d("Editor", "Selection ${textFieldValue.selection.start} ${textFieldValue.selection.end}")
                        textFieldValue = it.copy()
                    }
                }
            }
        }
        EditPanel(
            modifier = Modifier.align(Alignment.BottomCenter),
            textFieldValue
        ) {
            textFieldValue = it.copy()
            Log.d("EditPanel", "Selection ${textFieldValue.selection.start} ${textFieldValue.selection.end}")
        }
    }
}

@Composable
fun DisplayMarkDown(
    isEditableMode: Boolean,
    value: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
){
    var textColor = MaterialTheme.colorScheme.onBackground
    if (isEditableMode) {
        MarkdownEditor(
            value = value,
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
            markdown = value.text,
            color = textColor,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
        )
    }
}