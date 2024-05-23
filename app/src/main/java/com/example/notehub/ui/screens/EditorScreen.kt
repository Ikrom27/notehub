package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.ui.bars.EditorBar
import com.example.notehub.ui.components.EditPanel
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import com.example.notehub.viewmodels.EditorViewModel
import com.ikrom.twain.MarkdownEditor
import com.ikrom.twain.MarkdownText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("ResourceType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorScreen(
    navController: NavController,
    dirName: String,
    fileName: String,
    viewModel: EditorViewModel = hiltViewModel()
) {
    val file = File(FileUtils.ROOT_PATH.addPath(dirName).addPath(fileName))
    var isEditableMode by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(file.readText()))
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            contentColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            DateTimePickerDialog(fileName) {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }

            }
        }
    }

    Box {
        Scaffold(
            topBar = {
                EditorBar(
                    title = fileName,
                    isEditMode = isEditableMode,
                    onEditClick = {
                        isEditableMode = !isEditableMode
                                  },
                    onMoreClick = {
                        showBottomSheet = true
                    }
                )
            },

            ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding(), bottom = 56.dp)
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
fun DateTimePickerDialog(fileName: String, content: () -> DisposableHandle) {

}

@Composable
fun DisplayMarkDown(
    isEditableMode: Boolean,
    value: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
){
    var textColor = MaterialTheme.colorScheme.onBackground
    val context = LocalContext.current
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
                it.typeface = ResourcesCompat.getFont(context, R.font.inter_regular)
            }
        )
    } else {
        MarkdownText(
            markdown = value.text,
            color = textColor,
            fontSize = 18.sp,
            fontResource = R.font.inter_regular,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MAIN_HORIZONTAL_PADDING),
        )
    }
}

@Composable
fun AutoSave(
    textFieldValue: TextFieldValue,
    save: () -> Unit
){
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(LocalLifecycleOwner.current) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                save()
            }
        }
        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(textFieldValue) {
        delay(3000)
        save()
    }
}