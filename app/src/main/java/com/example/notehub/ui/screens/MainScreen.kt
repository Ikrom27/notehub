package com.example.notehub.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.constants.FILE_ITEMS_BETWEEN_PADDING
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.LABEL_DELETE
import com.example.notehub.constants.LABEL_RENAME
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.TITLE_SIZE
import com.example.notehub.constants.TITLE_WEIGHT
import com.example.notehub.constants.YOUR_FOLDER
import com.example.notehub.ui.components.AddIcon
import com.example.notehub.ui.components.CreateNewFolderDialog
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.utils.FileUtils
import com.example.notehub.viewmodels.MainViewModel
import java.io.File

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    var showCreateFolderDialog by remember { mutableStateOf(false) }

    var currentPath by remember { mutableStateOf(FileUtils.ROOT_PATH) }

    viewModel.updateFilesList(currentPath)

    FoldersList(
        viewModel = viewModel,
        onItemClick = {
            navController.navigate("NoteListScreen/${it.name}")
        },
        onAddClick = {showCreateFolderDialog = true},
        menuItems = {file ->
            DropdownMenuItem(
                text = {  Text(LABEL_DELETE) },
                onClick = { FileUtils.deleteFile(currentPath, file.name) }
            )
            DropdownMenuItem(
                text = { Text(LABEL_RENAME) },
                onClick = { FileUtils.renameTo(currentPath, file.name, "biba") }
            )
        }
    )

    if (showCreateFolderDialog) {
        CreateNewFolderDialog(
            onDismissRequest = {showCreateFolderDialog = false},
            confirmButton = {
                FileUtils.createDirectory(currentPath, it)
                viewModel.updateFilesList()
            }
        )
    }
}


@Composable
fun FoldersList(
    viewModel: MainViewModel,
    onItemClick: (File) -> Unit,
    menuItems: @Composable (File) -> Unit,
    onAddClick: () -> Unit
){
    val defaultDirectories by viewModel.defaultFolders.collectAsState()
    val directories by viewModel.fileList.collectAsState()


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(FILE_ITEMS_BETWEEN_PADDING),
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        items(items = defaultDirectories) {file ->
            FolderItem(
                title = file.name.substring(1),
                counter = 0,
                modifier = Modifier.clickable { onItemClick(file) }
            )
        }
        item {
            Text(
                text = YOUR_FOLDER,
                color = MaterialTheme.colorScheme.primary,
                fontSize = TITLE_SIZE,
                fontWeight = FontWeight(TITLE_WEIGHT),
                modifier = Modifier.padding(start = 17.dp, top = 33.dp)
            )
        }
        items(items = directories) { file ->
            FolderItemWithMenu(
                name = file.name,
                onItemClick = { onItemClick(file) },
                dropDownItems = {
                    menuItems(file)
                }
            )
        }
        item{
            AddIcon(onClick = {
                onAddClick()
            })
        }
    }
}

@Composable
fun FolderItemWithMenu(
    name: String,
    onItemClick: () -> Unit,
    dropDownItems: @Composable () -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }

    Card(
        modifier = Modifier
            .pointerInput(true) {
                detectTapGestures(
                    onPress = { onItemClick() },
                    onLongPress = {offset ->
                        pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp() - FILE_ITEM_HEIGHT)
                        expanded = true
                    }
                )
            }
    ) {
        FolderItem(
            title = name,
            counter = 0
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = pressOffset
        ) {
            dropDownItems()
        }
    }
}