package com.example.notehub.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notehub.constants.FILE_ITEMS_BETWEEN_PADDING
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.TITLE_SIZE
import com.example.notehub.constants.TITLE_WEIGHT
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.ui.theme.YOUR_FOLDER
import com.example.notehub.viewmodels.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val defaultDirectories by viewModel.defaultFolders.collectAsState()
    val directories by viewModel.fileList.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(FILE_ITEMS_BETWEEN_PADDING),
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        items(items = defaultDirectories) {
            FolderItem(
                title = it.name.substring(1),
                onClick = {},
                onLongClick = {},
                counter = 0
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
        items(items = directories) {
            FolderItem(
                title = it.name.substring(1),
                onClick = {},
                onLongClick = {},
                counter = 0
            )
        }
    }
}