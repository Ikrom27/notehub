package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.notehub.constants.MINIMAL_HEIGHT
import com.example.notehub.ui.theme.SEPARATOR_COLOR

@Composable
fun SeparatorDecoration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MINIMAL_HEIGHT)
            .background(SEPARATOR_COLOR))
}