package com.example.notehub.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.constants.FILE_ITEMS_BETWEEN_PADDING
import com.example.notehub.constants.FOLDER_FAVORITE
import com.example.notehub.constants.FOLDER_TEMPLATE
import com.example.notehub.constants.FOLDER_TRASH
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.TITLE_SIZE
import com.example.notehub.constants.TITLE_WEIGHT
import com.example.notehub.ui.components.AddIcon
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.ui.components.SetNameDialog
import com.example.notehub.ui.components.WithMenuItem
import com.example.notehub.utils.FileUtils
import com.example.notehub.viewmodels.MainViewModel
import java.io.File

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var showCreateFolderDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var currentPath by remember { mutableStateOf(FileUtils.ROOT_PATH) }

    viewModel.updateFilesList(currentPath)

    FoldersList(
        viewModel = viewModel,
        onItemClick = {
            navController.navigate("NoteListScreen/${it.name}")
        },
        onAddClick = {showCreateFolderDialog = true},
        menuItems = {file, hideMenu ->
            DropdownMenuItem(
                text = {  Text(stringResource(id = R.string.LABEL_DELETE)) },
                onClick = {
                    FileUtils.moveToTrash(currentPath, file.name)
                    viewModel.updateFilesList(currentPath)
                    hideMenu()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.LABEL_RENAME)) },
                onClick = {
                    showRenameDialog = true
                    viewModel.updateFilesList(currentPath)
                    hideMenu()
                }
            )

            if (showRenameDialog) {
                SetNameDialog(
                    defaultName = file.name,
                    onDismissRequest = {showRenameDialog = false},
                    confirmButton = {
                        FileUtils.renameTo(currentPath, file.name, it)
                    }
                )
            }
        }
    )

    if (showCreateFolderDialog) {
        SetNameDialog(
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
    menuItems: @Composable (File, onClick: () -> Unit) -> Unit,
    onAddClick: () -> Unit
){
    val defaultDirectories by viewModel.defaultFolders.collectAsState()
    val directories by viewModel.fileList.collectAsState()
    val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(FILE_ITEMS_BETWEEN_PADDING),
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        items(items = defaultDirectories) {file ->
            var name = file.name
            when (name){
                FOLDER_TEMPLATE -> name = getString(context, R.string.FOLDER_TEMPLATE)
                FOLDER_FAVORITE -> name = getString(context, R.string.FOLDER_FAVORITE)
                FOLDER_TRASH -> name = getString(context, R.string.FOLDER_TRASH)
            }
                FolderItem(
                    title = name,
                    counter = 0,
                    modifier = Modifier.clickable { onItemClick(file) }
                )
        }
        item {
            Text(
                text = stringResource(id = R.string.YOUR_FOLDER),
                color = MaterialTheme.colorScheme.primary,
                fontSize = TITLE_SIZE,
                fontWeight = FontWeight(TITLE_WEIGHT),
                modifier = Modifier.padding(start = 17.dp, top = 33.dp)
            )
        }
        items(items = directories) { file ->
            WithMenuItem(
                onItemClick = { onItemClick(file) },
                item = {
                    FolderItem(
                        title = file.name,
                        counter = 0
                    )
                },
                dropDownItems = {hideMenu ->
                    menuItems(file, hideMenu)
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