package app.sample.studio.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import app.sample.studio.theme.color.ChronoColorThemes
import app.sample.studio.theme.color.ChronoExtendedColors
import app.sample.studio.theme.color.ChronoExtendedColors.Companion.mapExtended
import app.sample.studio.theme.color.ChronoM3Colors
import app.sample.studio.theme.shape.ChronoShapes
import app.sample.studio.theme.typo.ChronoTypo

@Composable
fun ChronoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val (m3Colors, extendedColors) = remember(darkTheme) {
        val colors = when (darkTheme) {
            true -> ChronoColorThemes.Dark
            false -> ChronoColorThemes.Light
        }
        ChronoM3Colors.mapToColorScheme(colors) to colors.mapExtended()
    }

    CompositionLocalProvider(
        // Provide direct access through compose Local Content Providers for valid sources.
        // Colors makes sense because we're building them on top of the M3 system.
        // But other resources like Typo should and will just be used through `Cortex.Typo` anyway.

        // During this work sample there are extended colors - but this is how I would handle that
        with(ChronoExtendedColors) { LocalExtendedColors provides extendedColors },
        LocalContentColor provides m3Colors.onSurface
    ) {
        MaterialTheme(
            colorScheme = m3Colors,
            shapes = ChronoShapes.m3(),
            typography = ChronoTypo.m3(),
            content = content
        )
    }
}