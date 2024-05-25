package com.example.notehub.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.notehub.constants.FOLDER_FAVORITE
import com.example.notehub.constants.FOLDER_MAIN
import com.example.notehub.constants.FOLDER_TEMPLATE
import com.example.notehub.constants.FOLDER_TRASH
import java.io.File
import java.io.IOException

object FileUtils {
    val ROOT_PATH = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/${FOLDER_MAIN}"
    val TAG = "FileUtils"

    /**
     * generate default folders: Main, Favorite, Template, Trash
     */
    fun generateDirectories(context: Context) {
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
     * Creates file
     * @param parent: Parent directory path
     * @param fileName: File name which will be created
     */
    fun createFile(parent: String = ROOT_PATH, fileName: String): Boolean {
        val file = File(parent, fileName)
        return try {
            if (file.exists()) {
                Log.w(TAG, "File already exists: ${file.absolutePath}")
                false
            } else {
                if (file.createNewFile()) {
                    Log.d(TAG, "File created at ${file.absolutePath}")
                    true
                } else {
                    Log.e(TAG, "Failed to create file at $parent")
                    false
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error creating file at $parent", e)
            false
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

    /**
     * Move file or directory
     * @param oldParent: the current parent directory of the file or directory
     * @param newParent: the new parent directory for the file or directory
     * @param name: the name of the file or directory to move
     */
    fun moveFile(oldParent: String, newParent: String, name: String): Boolean {
        val oldFile = File(oldParent, name)
        val newFile = File(newParent, name)

        if (oldFile.exists()) {
            createDirectory(newParent, "")  // Ensure the new directory exists
            if (oldFile.renameTo(newFile)) {
                Log.d(TAG, "File or directory $name moved from $oldParent to $newParent")
                return true
            } else {
                Log.e(TAG, "Failed to move file or directory $name from $oldParent to $newParent")
                return false
            }
        } else {
            Log.e(TAG, "File or directory $name does not exist in $oldParent")
            return false
        }
    }

    /**
     * Move file or directory to trash
     * @param parent: the current parent directory of the file or directory
     * @param name: the name of the file or directory to move to trash
     */
    fun moveToTrash(parent: String = ROOT_PATH, name: String): Boolean {
        return moveFile(parent, "$ROOT_PATH/$FOLDER_TRASH", name)
    }
}