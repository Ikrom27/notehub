package com.example.notehub.data.data_source

import android.util.Log
import com.example.notehub.constants.FOLDER_FAVORITE
import com.example.notehub.constants.FOLDER_TEMPLATE
import com.example.notehub.constants.FOLDER_TRASH
import com.example.notehub.utils.FileUtils
import com.example.notehub.utils.addPath
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
    fun getDirList(directoryPath: String = ROOT_PATH): List<File> {
        val files = File(directoryPath)
        val subdirectories = mutableListOf<File>()

        files.listFiles()?.forEach { file ->
            if (isCorrectDir(file)) {
                subdirectories.add(file)
            }
        }
        return subdirectories
    }

    fun getFilesList(dir: String): List<File> {
        val files = File(FileUtils.ROOT_PATH.addPath(dir))
        val subdirectories = mutableListOf<File>()

        files.listFiles()?.forEach { file ->
            if (isCorrectFile(file)) {
                subdirectories.add(file)
            }
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
            File(ROOT_PATH +"/${FOLDER_FAVORITE}"),
            File(ROOT_PATH +"/${FOLDER_TEMPLATE}"),
            File(ROOT_PATH +"/${FOLDER_TRASH}")
        )
    }

    /**
     * Checks if the provided file is a correct file.
     *
     * @param file The file to check.
     * @return `true` if the file is a directory or has a ".md" extension and is not hidden, `false` otherwise.
     */
    fun isCorrectFile(file: File): Boolean{
        return file.extension.equals("md", ignoreCase = true) && !file.isHidden
    }

    fun isCorrectDir(file: File): Boolean{
        return file.isDirectory && !file.isHidden
    }

    companion object {
        /**
         * The root path for the local file system.
         */
        val ROOT_PATH = FileUtils.ROOT_PATH
    }
}