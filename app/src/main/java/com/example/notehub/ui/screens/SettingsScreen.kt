package com.example.notehub.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notehub.viewmodels.SettingsViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    ShowContent(
        onLoginClick = {
            viewModel.loginByGoogle()
            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun ShowContent(onLoginClick: () -> Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onLoginClick()
            }) {
            Text(text = "Login")
        }
    }
}