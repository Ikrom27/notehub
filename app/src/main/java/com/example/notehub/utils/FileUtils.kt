package com.example.notehub.utils

import android.os.Environment
import android.util.Log
import com.example.notehub.ui.theme.Strings
import java.io.File

object FileUtils {
    val ROOT_PATH = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/${Strings.FOLDER_MAIN}"
    val TAG = "FileUtils"

    /**
     * generate default folders: Main, Favorite, Template, Trash
     */
    fun generateDirectories(){
        createDirectory(ROOT_PATH, "")
        createDirectory(ROOT_PATH, Strings.FOLDER_FAVORITE)
        createDirectory(ROOT_PATH, Strings.FOLDER_TEMPLATE)
        createDirectory(ROOT_PATH, Strings.FOLDER_TRASH)
    }

    /**
     * creates directory
     * @param: directory name, which will be created
     */
    fun createDirectory(parent: String = ROOT_PATH, child: String) {
        val documentsDirectory = File(
            parent,
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
}