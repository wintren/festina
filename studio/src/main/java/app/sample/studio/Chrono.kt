package app.sample.studio

import androidx.compose.runtime.Composable
import app.sample.studio.theme.color.ChronoColorAccess
import app.sample.studio.theme.shape.ChronoShapeAccess
import app.sample.studio.theme.typo.ChronoTypoAccess

object Chrono {

    object Colors : ChronoColorAccess()
    object Typo : ChronoTypoAccess()
    object Shapes : ChronoShapeAccess()
    object Icons
    object Images

    val shapes @Composable get() = Shapes
    val colors @Composable get() = Colors
    val typo @Composable get() = Typo

    val icons get() = Icons
    val images get() = Images

}