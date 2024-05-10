package com.example.notehub.utils

import android.os.Environment
import android.util.Log
import com.example.notehub.constants.FOLDER_FAVORITE
import com.example.notehub.constants.FOLDER_MAIN
import com.example.notehub.constants.FOLDER_TEMPLATE
import com.example.notehub.constants.FOLDER_TRASH
import java.io.File

object FileUtils {
    val ROOT_PATH = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/${FOLDER_MAIN}"
    val TAG = "FileUtils"

    /**
     * generate default folders: Main, Favorite, Template, Trash
     */
    fun generateDirectories(){
        createDirectory(ROOT_PATH, "")
        createDirectory(ROOT_PATH, FOLDER_FAVORITE)
        createDirectory(ROOT_PATH, FOLDER_TEMPLATE)
        createDirectory(ROOT_PATH, FOLDER_TRASH)
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

    /**
    * Rename file or directory
    * @param oldName: the current name of the file or directory
    * @param newName: the new name for the file or directory
    */
    fun renameTo(parent: String, oldName: String, newName: String): Boolean {
        val oldFile = File(parent, oldName)
        val newFile = File(parent, newName)

        if (oldFile.exists()) {
            if (oldFile.renameTo(newFile)) {
                Log.d(TAG, "File or directory renamed from $oldName to $newName")
                return true
            } else {
                Log.e(TAG, "Failed to rename file or directory from $oldName to $newName")
                return false
            }
        } else {
            Log.e(TAG, "File or directory $oldName does not exist")
            return false
        }
    }

    /**
     * Delete file or directory
     * @param name: the name of the file or directory to delete
     */
    fun deleteFile(parent: String = ROOT_PATH, name: String): Boolean {
        val fileOrDirectory = File(parent, name)

        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.deleteRecursively()) {
                Log.d(TAG, "File or directory $name deleted successfully")
                return true
            } else {
                Log.e(TAG, "Failed to delete file or directory $name")
                return false
            }
        } else {
            Log.e(TAG, "File or directory $name does not exist")
            return false
        }
    }
}