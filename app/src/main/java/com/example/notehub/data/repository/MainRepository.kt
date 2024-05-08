package com.example.notehub.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notehub.data.data_source.GoogleDataSource
import com.example.notehub.data.data_source.LocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
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