package com.example.notehub.data.data_source

import android.content.Context
import android.os.Environment
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.notehub.ui.theme.Strings
import com.example.notehub.utils.FileUtils
import java.io.File

class LocalDataSource {

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

    fun getDefaultFolders(): List<File>{
        return listOf(
            File(ROOT_PATH +"/${Strings.FOLDER_FAVORITE}"),
            File(ROOT_PATH +"/${Strings.FOLDER_TEMPLATE}"),
            File(ROOT_PATH +"/${Strings.FOLDER_TRASH}")
        )
    }

    fun isCorrectFile(file: File): Boolean{
        return (file.isDirectory || file.extension.equals("md", ignoreCase = true)) && !file.isHidden
    }

    companion object {
        val ROOT_PATH = FileUtils.ROOT_PATH
    }


}