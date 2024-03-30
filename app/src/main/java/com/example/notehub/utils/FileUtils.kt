package com.example.notehub.utils

import android.os.Environment
import android.util.Log
import com.example.notehub.ui.theme.Strings
import java.io.File

object FileUtils {
    val ROOT_PATH = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/${Strings.FOLDER_MAIN}"
    val TAG = "FileUtils"

    /**
     * Gets list of files from currant
     * @param: currant directory path
     * @return: File list of default folders
     */
    fun getFilesList(directoryPath: String = ROOT_PATH): List<File> {
        val files = File(directoryPath)
        val subdirectories = mutableListOf<File>()

        if (isCorrectFile(files)) {
            files.listFiles()?.forEach { file ->
                if (isCorrectFile(file)) {
                    subdirectories.add(file)
                }
            }
        } else {
            throw IllegalArgumentException("The provided file is not a directory!")
        }
        return subdirectories
    }

    /**
     * Gets app default generates folders
     * @return: File list of default folders
     */
    fun getDefaultFolders(): List<File>{
        return listOf(
            File(ROOT_PATH+"/${Strings.FOLDER_FAVORITE}"),
            File(ROOT_PATH+"/${Strings.FOLDER_TEMPLATE}"),
            File(ROOT_PATH+"/${Strings.FOLDER_TRASH}")
        )
    }

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

    /**
     * check is the file directory or md file
     * @param: file, which will be chacked
     * @return: Boolean
     */
    fun isCorrectFile(file: File): Boolean{
        return (file.isDirectory || file.extension.equals("md", ignoreCase = true)) && !file.isHidden
    }
}