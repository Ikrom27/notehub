package com.example.notehub.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notehub.ui.screens.EditorScreen
import com.example.notehub.ui.screens.MainScreen
import com.example.notehub.ui.screens.NotesScreen
import com.example.notehub.ui.screens.SettingsScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavHostContainer(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ){
        composable("MainScreen"){
            MainScreen(navController)
        }
        composable("SettingsScreen"){
            SettingsScreen()
        }
        composable("EditorScreen/{dir}/{file}"){
            val dirName = it.arguments?.getString("dir")
            val fileName = it.arguments?.getString("file")
            if (dirName == null || fileName == null){
                navController.popBackStack()
            } else {
                EditorScreen(
                    navController = navController,
                    dirName = dirName,
                    fileName = fileName)
            }
        }
        composable("NoteListScreen/{dirName}"){
            val path = it.arguments?.getString("dirName")
            if (path == null){
                navController.popBackStack()
            } else {
                NotesScreen(navController, path)
            }
        }
    }
}