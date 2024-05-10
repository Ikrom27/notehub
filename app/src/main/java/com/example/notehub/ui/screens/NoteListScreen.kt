package com.example.notehub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notehub.R
import com.example.notehub.ui.components.FloatingButton
import com.example.notehub.ui.components.FolderItem
import com.example.notehub.viewmodels.NoteListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    navController: NavController,
    dirName: String,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    viewModel.updateFilesList(dirName)
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
                FloatingButton(
                    icon = R.drawable.ic_edit,
                    onClick = {  }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
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
}