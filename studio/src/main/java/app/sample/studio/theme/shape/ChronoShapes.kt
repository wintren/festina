package app.sample.studio.theme.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

object ChronoShapes {

    val None by lazy { RectangleShape }
    val Small by lazy { RoundedCornerShape(4.dp) }
    val Medium by lazy { RoundedCornerShape(8.dp) }
    val Large by lazy { RoundedCornerShape(16.dp) }
    val ExtraLarge by lazy { RoundedCornerShape(20.dp) }
    val Full by lazy { RoundedCornerShape(percent = 100) }

    fun m3(): Shapes = with(ChronoShapes) {
        Shapes(
            extraSmall = Small,
            small = Small,
            medium = Medium,
            large = Large,
            extraLarge = ExtraLarge,
        )
    }
}
