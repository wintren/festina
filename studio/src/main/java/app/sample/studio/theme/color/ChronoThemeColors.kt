package app.sample.studio.theme.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.studio.Chrono
import app.sample.studio.util.PreviewColumn

object ChronoColorThemes {

    val Light: ChronoColors by lazy {
        ChronoColors(
            light = true,
            primary = Color(0xFF3A608F),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFD3E4FF),
            onPrimaryContainer = Color(0xFF1F4876),
            secondary = Color(0xFF735C0C),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFE08B),
            onSecondaryContainer = Color(0xFF584400),
            tertiary = Color(0xFF8C4A60),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFFFD9E2),
            onTertiaryContainer = Color(0xFF703349),
            surface = Color(0xFFF9F9FF), // Using surfaceLight for surface
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF3F3FA),
            surfaceContainer = Color(0xFFEDEDF4),
            surfaceContainerHigh = Color(0xFFE7E8EE),
            surfaceContainerHighest = Color(0xFFE2E2E9),
            onSurface = Color(0xFF191C20), // Using onSurfaceLight for onSurface
            onSurfaceVariant = Color(0xFF40484D),
            outline = Color(0xFF71787D),
            outlineVariant = Color(0xFFC0C7CD),
            scrim = Color(0xFF000000),
            error = Color(0xFF904B3B),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD2),
            onErrorContainer = Color(0xFF723425),
            grass = Color(0xFF59B167),
            sky = Color(0xFF86A1FF),
            blaze = Color(0xFFFA5454),
        )
    }

    val Dark: ChronoColors by lazy {
        ChronoColors(
            light = false,
            primary = Color(0xFFA3C9FE),
            onPrimary = Color(0xFF00315C),
            primaryContainer = Color(0xFF1F4876),
            onPrimaryContainer = Color(0xFFD3E4FF),
            secondary = Color(0xFFE3C46D),
            onSecondary = Color(0xFF3D2F00),
            secondaryContainer = Color(0xFF584400),
            onSecondaryContainer = Color(0xFFFFE08B),
            tertiary = Color(0xFFFFB0C8),
            onTertiary = Color(0xFF541D32),
            tertiaryContainer = Color(0xFF703349),
            onTertiaryContainer = Color(0xFFFFD9E2),
            surface = Color(0xFF111318), // Using surfaceDark as surface
            surfaceContainerLowest = Color(0xFF0C0E13),
            surfaceContainerLow = Color(0xFF191C20),
            surfaceContainer = Color(0xFF1D2024),
            surfaceContainerHigh = Color(0xFF282A2F),
            surfaceContainerHighest = Color(0xFF33353A),
            onSurface = Color(0xFFE2E2E9), // Using onSurfaceDark as onSurface
            onSurfaceVariant = Color(0xFFC0C7CD),
            outline = Color(0xFF8A9297),
            outlineVariant = Color(0xFF40484D),
            scrim = Color(0xFF000000),
            error = Color(0xFFFFB4A3),
            onError = Color(0xFF561F12),
            errorContainer = Color(0xFF723425),
            onErrorContainer = Color(0xFFFFDAD2),
            grass = Color(0xFF59B167),
            sky = Color(0xFF86A1FF),
            blaze = Color(0xFFFA5454),
        )
    }
}

@Preview(heightDp = 1400)
@Composable
private fun PreviewLightColors() = PreviewColumn(showBackground = false) { AllColors() }

@Preview(heightDp = 1400)
@Composable
private fun PreviewDarkColors() = PreviewColumn(showBackground = false, darkMode = true) { AllColors() }

@Composable
private fun AllColors() {
    Column {
        ColorTextRow(text = "primary", color = Chrono.Colors.primary)
        ColorTextRow(text = "onPrimary", color = Chrono.Colors.onPrimary)
        ColorTextRow(text = "primaryContainer", color = Chrono.Colors.primaryContainer)
        ColorTextRow(text = "onPrimaryContainer", color = Chrono.Colors.onPrimaryContainer)
    }
    Column {
        ColorTextRow(text = "secondary", color = Chrono.Colors.secondary)
        ColorTextRow(text = "onSecondary", color = Chrono.Colors.onSecondary)
        ColorTextRow(text = "secondaryContainer", color = Chrono.Colors.secondaryContainer)
        ColorTextRow(text = "onSecondaryContainer", color = Chrono.Colors.onSecondaryContainer)
    }
    Column {
        ColorTextRow(text = "tertiary", color = Chrono.Colors.tertiary)
        ColorTextRow(text = "onTertiary", color = Chrono.Colors.onTertiary)
        ColorTextRow(text = "tertiaryContainer", color = Chrono.Colors.tertiaryContainer)
        ColorTextRow(text = "onTertiaryContainer", color = Chrono.Colors.onTertiaryContainer)
    }
    Column {
        ColorTextRow(text = "surface", color = Chrono.Colors.surface)
        ColorTextRow(text = "surfaceContainerLowest", color = Chrono.Colors.surfaceContainerLowest)
        ColorTextRow(text = "surfaceContainerLow", color = Chrono.Colors.surfaceContainerLow)
        ColorTextRow(text = "surfaceContainer", color = Chrono.Colors.surfaceContainer)
        ColorTextRow(text = "surfaceContainerHigh", color = Chrono.Colors.surfaceContainerHigh)
        ColorTextRow(text = "surfaceContainerHighest", color = Chrono.Colors.surfaceContainerHighest)
    }
    Column {
        ColorTextRow(text = "onSurface", color = Chrono.Colors.onSurface)
        ColorTextRow(text = "onSurfaceVariant", color = Chrono.Colors.onSurfaceVariant)
        ColorTextRow(text = "outline", color = Chrono.Colors.outline)
        ColorTextRow(text = "outlineVariant", color = Chrono.Colors.outlineVariant)
        ColorTextRow(text = "scrim", color = Chrono.Colors.scrim)
    }
    Column {
        ColorTextRow(text = "error", color = Chrono.Colors.error)
        ColorTextRow(text = "onError", color = Chrono.Colors.onError)
        ColorTextRow(text = "errorContainer", color = Chrono.Colors.errorContainer)
        ColorTextRow(text = "onErrorContainer", color = Chrono.Colors.onErrorContainer)
    }
    Column {
        ColorTextRow(text = "grass", color = Chrono.Colors.grass)
        ColorTextRow(text = "sky", color = Chrono.Colors.sky)
        ColorTextRow(text = "blaze", color = Chrono.Colors.blaze)
    }
}

@Composable
internal fun ColorTextRow(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
            .padding(6.dp),
        text = text,
        color = Chrono.Colors.contentColorFor(color),
        style = MaterialTheme.typography.labelLarge
    )
}
