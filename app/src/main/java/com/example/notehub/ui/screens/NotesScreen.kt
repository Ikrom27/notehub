package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.constants.FOLDER_FAVORITE
import com.example.notehub.constants.LABEL_ADD_TO_FAVORITE
import com.example.notehub.constants.LABEL_DELETE
import com.example.notehub.constants.LABEL_MOVE
import com.example.notehub.constants.LABEL_RENAME
import com.example.notehub.constants.LABEL_SET_REMINDER
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.NOTE_ITEM_WIDTH
import com.example.notehub.extansions.getNameWithoutExtension
import com.example.notehub.extansions.readPreview
import com.example.notehub.ui.components.FloatingButton
import com.example.notehub.ui.components.NoteItem
import com.example.notehub.ui.components.SetNameDialog
import com.example.notehub.ui.components.WithMenuItem
import com.example.notehub.ui.dialogs.DateTimePickerDialog
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import com.example.notehub.viewmodels.NoteListViewModel
import kotlinx.coroutines.launch
import java.io.File


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    dirName: String,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    viewModel.updateFilesList(dirName)
    val files by viewModel.fileList.collectAsState()
    var showCreateNoteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingButton(
                icon = R.drawable.ic_edit,
                onClick = {
                    showCreateNoteDialog = true
                }
            )
        }
    ) {
        NotesList(
            files = files,
            dirName = dirName,
            updateList = { viewModel.updateFilesList(dirName) },
            onItemClick = {
                navController.navigate("EditorScreen/$dirName/${it.name}")
            }
        )
    }

    if (showCreateNoteDialog) {
        SetNameDialog(
            onDismissRequest = {showCreateNoteDialog = false},
            confirmButton = {
                FileUtils.createFile(FileUtils.ROOT_PATH.addPath(dirName), "$it.md")
                viewModel.updateFilesList()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesList(
    onItemClick: (File) -> Unit,
    files: List<File>,
    dirName: String,
    updateList: () -> Unit
){
    var showRenameDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = NOTE_ITEM_WIDTH),
        horizontalArrangement = Arrangement.spacedBy(MAIN_HORIZONTAL_PADDING),
        ) {
        items(items = files){note ->
            WithMenuItem(
                onItemClick = {
                    onItemClick(note)
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
                            updateList()
                            hideMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(LABEL_DELETE) },
                        onClick = {
                            FileUtils.moveToTrash(FileUtils.ROOT_PATH.addPath(dirName), note.name)
                            updateList()
                            hideMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(LABEL_SET_REMINDER) },
                        onClick = {
                            showBottomSheet = true
                            updateList()
                            hideMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(LABEL_MOVE) },
                        onClick = {
                            //TODO: make move
                            updateList()
                            hideMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(LABEL_ADD_TO_FAVORITE) },
                        onClick = {
                            FileUtils.moveFile(
                                FileUtils.ROOT_PATH.addPath(dirName),
                                FileUtils.ROOT_PATH.addPath(FOLDER_FAVORITE),
                                note.name)
                            updateList()
                            hideMenu()
                        }
                    )
                }
            )
            if (showRenameDialog) {
                SetNameDialog(
                    defaultName = note.getNameWithoutExtension(),
                    onDismissRequest = {showRenameDialog = false},
                    confirmButton = {
                        FileUtils.renameTo(dirName, note.name + ".md", it)
                    }
                )
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    contentColor = MaterialTheme.colorScheme.surface,
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    DateTimePickerDialog(note.getNameWithoutExtension()) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }

                    }
                }
            }
        }
    }
}