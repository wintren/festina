package app.sample.studio.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal interface ChronoM3Colors {
    // Primary
    val primary: Color
    val onPrimary: Color
    val primaryContainer: Color
    val onPrimaryContainer: Color
    //  Not Used: inversePrimary

    // Secondary
    val secondary: Color
    val onSecondary: Color
    val secondaryContainer: Color
    val onSecondaryContainer: Color

    // Tertiary
    val tertiary: Color
    val onTertiary: Color
    val tertiaryContainer: Color
    val onTertiaryContainer: Color

    val surface: Color
    // Not Used: surfaceVariant, surfaceBright, surfaceDim, inverseSurface

    val onSurface: Color
    val onSurfaceVariant: Color
    // Not Used: inverseOnSurface

    val outline: Color
    val outlineVariant: Color

    val scrim: Color

    val error: Color
    val onError: Color
    val errorContainer: Color
    val onErrorContainer: Color

    val surfaceContainerLowest: Color
    val surfaceContainerLow: Color
    val surfaceContainer: Color
    val surfaceContainerHigh: Color
    val surfaceContainerHighest: Color

    /*
        Not in Latest M3 Colors
        * background
        * onBackground
        * surfaceTint
     */

    companion object {
        fun mapToColorScheme(colors: ChronoColors): ColorScheme = with(colors) {
            when (light) {
                true -> lightColorScheme(
                    primary = primary,
                    onPrimary = onPrimary,
                    primaryContainer = primaryContainer,
                    onPrimaryContainer = onPrimaryContainer,
                    secondary = secondary,
                    onSecondary = onSecondary,
                    secondaryContainer = secondaryContainer,
                    onSecondaryContainer = onSecondaryContainer,
                    tertiary = tertiary,
                    onTertiary = onTertiary,
                    tertiaryContainer = tertiaryContainer,
                    onTertiaryContainer = onTertiaryContainer,
                    surface = surface,
                    onSurface = onSurface,
                    onSurfaceVariant = onSurfaceVariant,
                    error = error,
                    onError = onError,
                    errorContainer = errorContainer,
                    onErrorContainer = onErrorContainer,
                    outline = outline,
                    outlineVariant = outlineVariant,
                    scrim = scrim,
                    surfaceContainer = surfaceContainer,
                    surfaceContainerHigh = surfaceContainerHigh,
                    surfaceContainerHighest = surfaceContainerHighest,
                    surfaceContainerLow = surfaceContainerLow,
                    surfaceContainerLowest = surfaceContainerLowest,
                    background = surface,
                    onBackground = onSurface,

//                    inversePrimary =,
//                    surfaceVariant =,
//                    surfaceTint =,
//                    inverseSurface =,
//                    inverseOnSurface =,
//                    surfaceBright =,
//                    surfaceDim =,
                )
                false -> darkColorScheme(
                    primary = primary,
                    onPrimary = onPrimary,
                    primaryContainer = primaryContainer,
                    onPrimaryContainer = onPrimaryContainer,
                    secondary = secondary,
                    onSecondary = onSecondary,
                    secondaryContainer = secondaryContainer,
                    onSecondaryContainer = onSecondaryContainer,
                    tertiary = tertiary,
                    onTertiary = onTertiary,
                    tertiaryContainer = tertiaryContainer,
                    onTertiaryContainer = onTertiaryContainer,
                    surface = surface,
                    onSurface = onSurface,
                    onSurfaceVariant = onSurfaceVariant,
                    error = error,
                    onError = onError,
                    errorContainer = errorContainer,
                    onErrorContainer = onErrorContainer,
                    outline = outline,
                    outlineVariant = outlineVariant,
                    scrim = scrim,
                    surfaceContainer = surfaceContainer,
                    surfaceContainerHigh = surfaceContainerHigh,
                    surfaceContainerHighest = surfaceContainerHighest,
                    surfaceContainerLow = surfaceContainerLow,
                    surfaceContainerLowest = surfaceContainerLowest,
                    background = surface,
                    onBackground = onSurface,

//                    inversePrimary =,
//                    surfaceVariant =,
//                    surfaceTint =,
//                    inverseSurface =,
//                    inverseOnSurface =,
//                    surfaceBright =,
//                    surfaceDim =,
                )
            }
        }
    }
}
