package com.example.notehub

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.notehub.ui.screens.EditorScreen
import com.example.notehub.ui.theme.NoteHubTheme
import com.example.notehub.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        }
        setContent {
            NoteHubTheme {
                Box(Modifier.safeDrawingPadding()){
                    EditorScreen()
                }
            }
        }
        FileUtils.generateDirectories()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}