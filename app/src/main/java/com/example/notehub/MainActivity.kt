package com.example.notehub

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.credentials.CredentialManager
import com.example.notehub.ui.screens.MainScreen
import com.example.notehub.ui.screens.SettingsScreen
import com.example.notehub.ui.theme.NoteHubTheme
import com.example.notehub.ui.theme.Strings
import com.example.notehub.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MainScreen()
                    SettingsScreen()
                }
            }
        }
        FileUtils.generateDirectories()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}