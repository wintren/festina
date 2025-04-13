package app.sample.studio.theme.color

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import app.sample.studio.util.isPreview

/**
 * It's not possible to inherit from [ChronoM3Colors] and [ChronoExtendedColors] because these need to be composable,
 * and since the interface values aren't then these can't be.
 * But this is just an accessor and you will never "use" the interface, so it's fine to just make sure that there's always a 1:1 relationship.
 */
abstract class ChronoColorAccess {

    /* === Material 3 === */
    val primary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.primary
    val onPrimary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onPrimary
    val primaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onPrimaryContainer
    val secondary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.secondary
    val onSecondary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSecondary
    val secondaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.secondaryContainer
    val onSecondaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSecondaryContainer
    val tertiary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.tertiary
    val onTertiary: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onTertiary
    val tertiaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.tertiaryContainer
    val onTertiaryContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onTertiaryContainer
    val surface: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surface
    val surfaceVariant: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceVariant
    val onSurface: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSurfaceVariant
    val outline: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.outline
    val outlineVariant: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.outlineVariant
    val scrim: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.scrim
    val error: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.error
    val onError: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onError
    val errorContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.errorContainer
    val onErrorContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onErrorContainer
    val surfaceContainerLowest: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceContainerLowest
    val surfaceContainerLow: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceContainerLow
    val surfaceContainer: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceContainer
    val surfaceContainerHigh: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceContainerHigh
    val surfaceContainerHighest: Color
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceContainerHighest

    /* === Extended === */
    val grass: Color
        @Composable @ReadOnlyComposable get() = ChronoExtendedColors.colors.grass
    val sky: Color
        @Composable @ReadOnlyComposable get() = ChronoExtendedColors.colors.sky
    val blaze: Color
        @Composable @ReadOnlyComposable get() = ChronoExtendedColors.colors.blaze

    @Composable
    private fun previewOnly(color: Color) = when {
        isPreview() -> color
        else -> error("This color is not applicable to have a content color")
    }

    @Composable
    fun contentColorFor(color: Color): Color {
        return when (color) {
            primary -> onPrimary
            onPrimary -> previewOnly(primary)
            primaryContainer -> onPrimaryContainer
            onPrimaryContainer -> previewOnly(primaryContainer)

            secondary -> onSecondary
            onSecondary -> previewOnly(color = secondary)
            secondaryContainer -> onSecondaryContainer
            onSecondaryContainer -> previewOnly(color = secondaryContainer)

            tertiary -> onTertiary
            onTertiary -> previewOnly(color = tertiary)
            tertiaryContainer -> onTertiaryContainer
            onTertiaryContainer -> previewOnly(color = tertiaryContainer)

            surface -> onSurface
            surfaceVariant -> onSurface
            surfaceContainerLowest -> onSurface
            surfaceContainerLow -> onSurface
            surfaceContainer -> onSurface
            surfaceContainerHigh -> onSurface
            surfaceContainerHighest -> onSurface

            onSurface -> previewOnly(color = surface)
            onSurfaceVariant -> previewOnly(color = surface)
            outline -> previewOnly(color = surface)
            outlineVariant -> previewOnly(color = surface)
            scrim -> previewOnly(color = surface)

            error -> onError
            onError -> previewOnly(color = error)
            errorContainer -> onErrorContainer
            onErrorContainer -> previewOnly(color = errorContainer)

            grass -> previewOnly(color = Color.White)
            sky -> previewOnly(color = Color.White)
            blaze -> previewOnly(color = Color.White)
            else -> error("No content color for color $color")
        }
    }
}
