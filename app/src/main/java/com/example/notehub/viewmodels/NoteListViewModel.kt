package com.example.notehub.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notehub.data.data_source.LocalDataSource
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _fileList = MutableStateFlow<List<File>>(emptyList())
    val fileList = _fileList

    init {
        updateFilesList()
    }

    fun updateFilesList(dir: String = LocalDataSource.ROOT_PATH) {
        _fileList.value = repository.getFiles(dir)
    }
}
