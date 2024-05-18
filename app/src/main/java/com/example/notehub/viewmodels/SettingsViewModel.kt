package com.example.notehub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
    fun uploadToStorage(userUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.uploadToStorage(userUid)
        }
    }
}