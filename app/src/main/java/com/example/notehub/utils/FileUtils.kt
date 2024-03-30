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
        createDirectory(Strings.FOLDER_MAIN)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_FAVORITE)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_TEMPLATE)
        createDirectory(Strings.FOLDER_MAIN + "/" + Strings.FOLDER_TRASH)
    }

    /**
     * creates directory
     * @param: directory name, which will be created
     */
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
}