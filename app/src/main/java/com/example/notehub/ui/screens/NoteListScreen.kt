package com.example.notehub.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.viewmodels.NoteListViewModel

@Composable
fun NoteListScreen(
    navController: NavController,
    dirName: String,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    viewModel.updateFilesList(dirName)
    val files by viewModel.fileList.collectAsState()

    LazyColumn {
        items(items = files){
            Box(
                modifier = Modifier.clickable { navController.navigate("EditorScreen/${dirName}/${it.name}") },
            ){
                FolderItem(
                    title = it.name,
                    counter = 0
                )
            }
        }
    }
}