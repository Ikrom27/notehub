package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.notehub.R
import com.example.notehub.constants.FLOATING_ICON_SIZE
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

@Composable
fun FloatingButton(
    onClick: () -> Unit,
    icon: Int
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(FLOATING_ICON_SIZE)
            .background(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary)
    ) {
            Icon(
                modifier = Modifier.size(DimensCalculator.calculateIconSize(FLOATING_ICON_SIZE)),
                painter = painterResource(icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
    }
}