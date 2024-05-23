package com.example.notehub.data.repository

import com.example.notehub.data.data_source.GoogleDataSource
import com.example.notehub.data.data_source.LocalDataSource
import java.io.File
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val googleDataSource: GoogleDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getDirList(directoryPath: String = LocalDataSource.ROOT_PATH): List<File> {
        return localDataSource.getDirList(directoryPath)
    }

    fun getFiles(dir: String): List<File> {
        return localDataSource.getFilesList(dir)
    }

    fun getDefaultFolders(): List<File>{
        return localDataSource.getDefaultFolders()
    }

    fun saveFile(dirName: String, fileName: String, content: String) {
        localDataSource.saveFile(dirName, fileName, content)
    }
}