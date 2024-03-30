package com.example.notehub.data.repository

import com.example.notehub.data.data_source.GoogleDataSource
import com.example.notehub.data.data_source.LocalDataSource
import com.example.notehub.ui.theme.Strings
import java.io.File
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val googleDataSource: GoogleDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getFilesList(directoryPath: String = LocalDataSource.ROOT_PATH): List<File> {
        return localDataSource.getFilesList(directoryPath)
    }

    fun getDefaultFolders(): List<File>{
        return localDataSource.getDefaultFolders()
    }
}