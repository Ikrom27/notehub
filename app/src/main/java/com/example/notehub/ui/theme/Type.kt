package com.example.notehub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.notehub.R

val INTER_FAMILY = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold)
)
private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = INTER_FAMILY),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = INTER_FAMILY),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = INTER_FAMILY),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = INTER_FAMILY),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = INTER_FAMILY),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = INTER_FAMILY),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = INTER_FAMILY),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = INTER_FAMILY),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = INTER_FAMILY),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = INTER_FAMILY),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = INTER_FAMILY),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = INTER_FAMILY),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = INTER_FAMILY),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = INTER_FAMILY),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = INTER_FAMILY)
)