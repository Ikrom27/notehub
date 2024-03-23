package com.example.notehub.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
    fun loginByGoogle(){

    }
}