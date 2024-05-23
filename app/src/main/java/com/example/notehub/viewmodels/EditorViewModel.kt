package com.example.notehub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
    fun saveFile(dirName: String, fileName: String, content: String){
        viewModelScope.launch {
            repository.saveFile(dirName, fileName, content)
        }
    }
}