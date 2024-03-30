package com.example.notehub.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notehub.data.data_source.LocalDataSource
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
    private val _fileList = MutableStateFlow<List<File>>(emptyList())
    private val _defaultFolders = MutableStateFlow<List<File>>(emptyList())
    val fileList = _fileList
    val defaultFolders = _defaultFolders

    init {
        updateFilesList()
        updateDefaultFolders()
    }

    fun updateFilesList(directoryPath: String = LocalDataSource.ROOT_PATH) {
        _fileList.value = repository.getFilesList(directoryPath)
    }

    fun updateDefaultFolders() {
        _defaultFolders.value = repository.getDefaultFolders()
    }
}