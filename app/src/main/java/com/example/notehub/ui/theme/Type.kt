package com.example.notehub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.notehub.R

val WMD_FAMILY = FontFamily(
    Font(R.font.wmd_bold, FontWeight.Bold),
    Font(R.font.wmd_extrabold, FontWeight.ExtraBold),
    Font(R.font.wmd_medium, FontWeight.Medium),
    Font(R.font.wmd_regular, FontWeight.Normal),
    Font(R.font.wmd_semibold, FontWeight.SemiBold)
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = WMD_FAMILY),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = WMD_FAMILY),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = WMD_FAMILY),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = WMD_FAMILY),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = WMD_FAMILY),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = WMD_FAMILY),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = WMD_FAMILY),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = WMD_FAMILY),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = WMD_FAMILY),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = WMD_FAMILY),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = WMD_FAMILY),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = WMD_FAMILY),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = WMD_FAMILY),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = WMD_FAMILY),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = WMD_FAMILY)
)