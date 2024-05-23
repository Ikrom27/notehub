package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.LABEL_DELETE
import com.example.notehub.constants.LABEL_RENAME
import com.example.notehub.extansions.getNameWithoutExtension
import com.example.notehub.extansions.readPreview
import com.example.notehub.ui.components.FloatingButton
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.ui.components.NoteItem
import com.example.notehub.ui.components.SetNameDialog
import com.example.notehub.ui.components.WithMenuItem
import com.example.notehub.utils.FileUtils
import com.example.notehub.viewmodels.NoteListViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    navController: NavController,
    dirName: String,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    viewModel.updateFilesList(dirName)
    var showRenameDialog by remember { mutableStateOf(false) }
    val files by viewModel.fileList.collectAsState()
    Scaffold(
        topBar = {},
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                FloatingButton(
                    icon = R.drawable.ic_edit,
                    onClick = {  }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn {
            items(items = files){note ->
                WithMenuItem(
                    onItemClick = {
                        navController.navigate("EditorScreen/$dirName/${note.name}")
                    },
                    item = {
                        NoteItem(
                            title = note.getNameWithoutExtension(),
                            noteText = note.readPreview()
                        )
                    },
                    dropDownItems = {hideMenu ->
                        DropdownMenuItem(
                            text = { Text(LABEL_RENAME) },
                            onClick = {
                                showRenameDialog = true
                                viewModel.updateFilesList(dirName)
                                hideMenu()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(LABEL_RENAME) },
                            onClick = {
                                showRenameDialog = true
                                viewModel.updateFilesList(dirName)
                                hideMenu()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(LABEL_RENAME) },
                            onClick = {
                                showRenameDialog = true
                                viewModel.updateFilesList(dirName)
                                hideMenu()
                            }
                        )
                    }
                )
                if (showRenameDialog) {
                    SetNameDialog(
                        defaultName = note.name,
                        onDismissRequest = {showRenameDialog = false},
                        confirmButton = {
                            FileUtils.renameTo(dirName, note.name, it)
                        }
                    )
                }
            }
        }
    }
}