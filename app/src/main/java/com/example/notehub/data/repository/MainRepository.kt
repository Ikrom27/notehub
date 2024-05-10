package com.example.notehub.data.repository

import com.example.notehub.data.data_source.GoogleDataSource
import com.example.notehub.data.data_source.LocalDataSource
import java.io.File
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val googleDataSource: GoogleDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getFilesList(directoryPath: String = LocalDataSource.ROOT_PATH): List<File> {
        return localDataSource.getDirList(directoryPath)
    }

    fun getDefaultFolders(): List<File>{
        return localDataSource.getDefaultFolders()
    }
}