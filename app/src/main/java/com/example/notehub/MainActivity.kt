package com.example.notehub

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.notehub.ui.screens.MainScreen
import com.example.notehub.ui.screens.SettingsScreen
import com.example.notehub.ui.theme.NoteHubTheme
import com.example.notehub.ui.theme.Strings
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
        generateDirectories()
    }

    private fun createDirectory(child: String) {
        val documentsDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            child
        )
        if (!documentsDirectory.exists()) {
            if (documentsDirectory.mkdirs()) {
                Log.d(TAG, "Directory created at ${documentsDirectory.absolutePath}")
            } else {
                Log.e(TAG, "Failed to create directory")
            }
        }
    }

    private fun generateDirectories(){
        createDirectory(Strings.FOLDER_MAIN)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_FAVORITE)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_TEMPLATE)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_TRASH)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}