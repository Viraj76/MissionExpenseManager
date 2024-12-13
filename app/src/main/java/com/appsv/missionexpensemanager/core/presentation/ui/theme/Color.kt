package com.appsv.missionexpensemanager.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val ColorPrimary = Color(0xFF4C3CCE)
val ColorSecondary = Color(0xFF6F62D8)
val ColorSecondaryVariant = Color(0xFFF7F7FE)

val GrayishPurple = Color(0xFF767587)
val DarkGrayishPurple = Color(0xFF494863)
val LightGrayishBlue = Color(0xFFEBEFF5)

val GrayishBlue = Color(0xFFA1A1AB)
val LighterDarkColor = Color(0xFF3A3A3A)



@Composable
fun getColorsForTheme(): ColorPalette {
    val isDarkTheme = isSystemInDarkTheme()

    val ColorPrimary = if (isDarkTheme) Color(0xFF6F62D8) else Color(0xFF4C3CCE)
    val ColorSecondary = if (isDarkTheme) Color(0xFF8E80D8) else Color(0xFF6F62D8)
    val ColorSecondaryVariant = if (isDarkTheme) Color(0xFF303030) else Color(0xFFF7F7FE)

    val GrayishPurple = if (isDarkTheme) Color(0xFF767587) else Color(0xFF767587)
    val DarkGrayishPurple = if (isDarkTheme) Color.LightGray else Color(0xFF494863)
    val LightGrayishBlue = if (isDarkTheme) Color(0xFF2E2E3E) else Color(0xFFEBEFF5)

    val GrayishBlue = if (isDarkTheme) Color(0xFF707070) else Color(0xFFA1A1AB)

    return ColorPalette(
        ColorPrimary = ColorPrimary,
        ColorSecondary = ColorSecondary,
        ColorSecondaryVariant = ColorSecondaryVariant,
        GrayishPurple = GrayishPurple,
        DarkGrayishPurple = DarkGrayishPurple,
        LightGrayishBlue = LightGrayishBlue,
        GrayishBlue = GrayishBlue
    )
}


data class ColorPalette(
    val ColorPrimary: Color,
    val ColorSecondary: Color,
    val ColorSecondaryVariant: Color,
    val GrayishPurple: Color,
    val DarkGrayishPurple: Color,
    val LightGrayishBlue: Color,
    val GrayishBlue: Color
)


