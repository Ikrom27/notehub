package com.example.notehub.utils

import androidx.compose.ui.unit.Dp

object DimensCalculator {
    fun calculateRadius(size: Dp): Dp {
        return size * 0.1f
    }

    fun calculateIconSize(size: Dp): Dp {
        return size * 0.6f
    }
}