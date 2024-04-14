package com.example.notehub.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.notehub.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun loginByGoogle(context: Context, coroutineScope: CoroutineScope) {
        repository.setGoogleAuth(context, coroutineScope)
    }

}