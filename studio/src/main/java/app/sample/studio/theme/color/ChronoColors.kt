package app.sample.studio.theme.color

import androidx.compose.ui.graphics.Color

data class ChronoColors(
    val light: Boolean,
    // Primary
    override val primary: Color,
    override val onPrimary: Color,
    override val primaryContainer: Color,
    override val onPrimaryContainer: Color,

    // Secondary
    override val secondary: Color,
    override val onSecondary: Color,
    override val secondaryContainer: Color,
    override val onSecondaryContainer: Color,

    // Tertiary
    override val tertiary: Color,
    override val onTertiary: Color,
    override val tertiaryContainer: Color,
    override val onTertiaryContainer: Color,

    // Surface
    override val surface: Color,

    // Surface Container
    override val surfaceContainerLowest: Color,
    override val surfaceContainerLow: Color,
    override val surfaceContainer: Color,
    override val surfaceContainerHigh: Color,
    override val surfaceContainerHighest: Color,

    // On Surface
    override val onSurface: Color,
    override val onSurfaceVariant: Color,
    override val outline: Color,
    override val outlineVariant: Color,

    // Overlays
    override val scrim: Color,

    // Error
    override val error: Color,
    override val onError: Color,
    override val errorContainer: Color,
    override val onErrorContainer: Color,

    // Custom / Extended
    override val grass: Color,
    override val sky: Color,
    override val blaze: Color,
) : ChronoM3Colors, ChronoExtendedColors
