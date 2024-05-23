package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.constants.LABEL_RENAME
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.NOTE_ITEM_WIDTH
import com.example.notehub.extansions.getNameWithoutExtension
import com.example.notehub.extansions.readPreview
import com.example.notehub.ui.components.FloatingButton
import com.example.notehub.ui.components.NoteItem
import com.example.notehub.ui.components.SetNameDialog
import com.example.notehub.ui.components.WithMenuItem
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
import com.example.notehub.viewmodels.NoteListViewModel
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

@Composable
fun NotesList(
    onItemClick: (File) -> Unit,
    files: List<File>,
    dirName: String,
    updateList: () -> Unit
){
    var showRenameDialog by remember { mutableStateOf(false) }
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
                        text = { Text(LABEL_RENAME) },
                        onClick = {
                            showRenameDialog = true
                            updateList()
                            hideMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(LABEL_RENAME) },
                        onClick = {
                            showRenameDialog = true
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
        }
    }
}