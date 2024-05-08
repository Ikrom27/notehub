package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.notehub.utils.DimensCalculator


@Composable
fun SelectableButton(
    icon: Int,
    selected: Boolean,
    size: Dp,
    onClick: () -> Unit,
){
    Box(modifier = Modifier
        .size(size)
        .background(
            shape = RoundedCornerShape(DimensCalculator.calculateRadius(size)),
            color = if (selected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
        )
    ){
        IconButton(
            onClick = { onClick() },
        ) {
            Icon(painter = painterResource(
                id = icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.height(DimensCalculator.calculateIconSize(size))
            )
        }
    }
}