package app.sample.studio.util

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.studio.Chrono
import app.sample.studio.theme.ChronoTheme

@Preview(group = "Default", name = "Default")
@Preview(group = "Default", name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(group = "Locale", name = "Finnish", locale = "fi-rFI")
@Preview(group = "Locale", name = "Swedish", locale = "sv-rSE")
@Preview(group = "Wonky", name = "Crap Device", device = "id:Nexus One")
@Preview(group = "Wonky", name = "Very Large Fonts", fontScale = 2F)
@Preview(group = "Wonky", name = "Large Fonts", fontScale = 1.5F)
@Preview(group = "Wonky", name = "Small Font", fontScale = 0.5F)
annotation class FullPreview

@Preview(group = "Default", name = "Default")
@Preview(group = "Default", name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(group = "Wonky", name = "Very Large Fonts", fontScale = 2F)
@Preview(group = "Wonky", name = "Small Font", fontScale = 0.5F)
annotation class ComponentPreview

@Composable
fun isPreview(): Boolean = LocalInspectionMode.current

private val contentPadding = Modifier.padding(16.dp)
private val contentSpacing = Arrangement.spacedBy(8.dp)

@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    darkMode: Boolean = isSystemInDarkTheme(),
    showBackground: Boolean = true,
    addPadding: Boolean = true,
    previewContent: @Composable BoxScope.() -> Unit
) {
    ChronoTheme(darkTheme = darkMode) {
        val previewBox: @Composable () -> Unit = {
            Box(
                modifier = modifier.thenIf(addPadding) { contentPadding },
                content = previewContent
            )
        }
        when (showBackground) {
            true -> PreviewSurface(content = previewBox)
            false -> previewBox()
        }
    }
}

@Composable
fun PreviewRow(
    modifier: Modifier = Modifier,
    darkMode: Boolean = isSystemInDarkTheme(),
    showBackground: Boolean = true,
    addPadding: Boolean = true,
    addSpacing: Boolean = true,
    previewContent: @Composable RowScope.() -> Unit
) {
    val previewRow: @Composable () -> Unit = {
        Row(
            modifier = modifier.thenIf(addPadding) { contentPadding },
            horizontalArrangement = if (addSpacing) contentSpacing else Arrangement.Start,
            content = previewContent
        )
    }
    ChronoTheme(darkTheme = darkMode) {
        when (showBackground) {
            true -> PreviewSurface(content = previewRow)
            false -> previewRow()
        }
    }
}

@Composable
fun PreviewColumn(
    modifier: Modifier = Modifier,
    darkMode: Boolean = isSystemInDarkTheme(),
    showBackground: Boolean = true,
    addPadding: Boolean = true,
    addSpacing: Boolean = true,
    previewContent: @Composable ColumnScope.() -> Unit
) {
    val previewRow: @Composable () -> Unit = {
        Column(
            modifier = modifier.thenIf(addPadding) { contentPadding },
            verticalArrangement = if (addSpacing) contentSpacing else Arrangement.Top,
            content = previewContent
        )
    }
    ChronoTheme(darkTheme = darkMode) {
        when (showBackground) {
            true -> PreviewSurface(content = previewRow)
            false -> previewRow()
        }
    }
}

@Composable
private fun PreviewSurface(
    content: @Composable () -> Unit
) {
    Surface(
        color = Chrono.colors.surface,
        content = content
    )
}
