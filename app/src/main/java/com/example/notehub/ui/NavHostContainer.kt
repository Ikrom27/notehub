package com.example.notehub.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notehub.ui.screens.EditorScreen
import com.example.notehub.ui.screens.MainScreen
import com.example.notehub.ui.screens.SettingsScreen

@Composable
fun NavHostContainer(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "SettingsScreen"
    ){
        composable("MainScreen"){
            MainScreen(navController)
        }
        composable("SettingsScreen"){
            SettingsScreen(navController)
        }
        composable("EditorScreen"){
            EditorScreen(navController)
        }
    }
}