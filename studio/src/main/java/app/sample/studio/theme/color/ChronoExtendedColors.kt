package app.sample.studio.theme.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Only here to provide some custom colors beyond M3.
 */
internal interface ChronoExtendedColors {

    val grass: Color
    val sky: Color
    val blaze: Color

    /** Needed to have something to provide in Local Content */
    data class ExtendedColors(
        override val grass: Color,
        override val sky: Color,
        override val blaze: Color
    ) : ChronoExtendedColors

    companion object {
        val LocalExtendedColors = staticCompositionLocalOf {
            ExtendedColors(
                grass = Color.Unspecified,
                sky = Color.Unspecified,
                blaze = Color.Unspecified
            )
        }

        val colors: ExtendedColors
            @Composable
            @ReadOnlyComposable
            get() = LocalExtendedColors.current

        fun ChronoExtendedColors.mapExtended(): ExtendedColors =
            ExtendedColors(
                grass = grass,
                sky = sky,
                blaze = blaze
            )
    }
}
