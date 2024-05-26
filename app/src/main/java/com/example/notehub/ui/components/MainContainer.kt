package com.example.notehub.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.notehub.ui.NavHostContainer
import com.example.notehub.ui.bars.CustomTopAppBar

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MainContainer() {
    Scaffold(
        topBar = { /*CustomTopAppBar()*/}
    ) {
        innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHostContainer()
    }
}