package com.example.notehub.data.data_source

import android.os.Environment
import com.example.notehub.ui.theme.Strings
import com.example.notehub.utils.FileUtils
import java.io.File

/**
 * A class responsible for providing access to local files and directories.
 *
 * @property ROOT_PATH The root path for the local file system.
 */
class LocalDataSource {

    /**
     * Retrieves a list of files within the specified directory path.
     *
     * @param directoryPath The path of the directory. Defaults to [ROOT_PATH].
     * @return A list of files within the directory.
     * @throws IllegalArgumentException If the provided path is not a directory.
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
     * Retrieves a list of default folders.
     *
     * @return A list of default folders.
     */
    fun getDefaultFolders(): List<File>{
        return listOf(
            File(ROOT_PATH +"/${Strings.FOLDER_FAVORITE}"),
            File(ROOT_PATH +"/${Strings.FOLDER_TEMPLATE}"),
            File(ROOT_PATH +"/${Strings.FOLDER_TRASH}")
        )
    }

    /**
     * Checks if the provided file is a correct file.
     *
     * @param file The file to check.
     * @return `true` if the file is a directory or has a ".md" extension and is not hidden, `false` otherwise.
     */
    fun isCorrectFile(file: File): Boolean{
        return (file.isDirectory || file.extension.equals("md", ignoreCase = true)) && !file.isHidden
    }

    companion object {
        /**
         * The root path for the local file system.
         */
        val ROOT_PATH = FileUtils.ROOT_PATH
    }
}