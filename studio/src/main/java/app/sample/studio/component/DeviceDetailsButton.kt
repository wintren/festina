package app.sample.studio.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.studio.Chrono
import app.sample.studio.util.PreviewColumn

object DeviceDetailsButton {

    private val buttonShape: CornerBasedShape @Composable get() = Chrono.shapes.medium

    enum class ConnectingStatus {
        Disconnected, Connecting, Connected;

        /** This would of course have access to R.string handling, which I think can be owned by Studio overall as it should be the repository for everything user facing. */
        fun label() = when (this) {
            Disconnected -> "Disconnected"
            Connecting -> "Connecting..."
            Connected -> "Connected"
        }
    }

    @Composable
    operator fun invoke(
        id: String,
        active: Boolean,
        status: ConnectingStatus,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {

        val containerColor = if (active) Chrono.colors.primaryContainer else Chrono.colors.surfaceContainer
        Row(
            modifier
                .border(width = 1.dp, color = Chrono.colors.outline, shape = buttonShape)
                .clip(buttonShape)
                .background(color = containerColor)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.size(20.dp)) {
                AnimatedContent(status) { animatedStatus ->
                    when (animatedStatus) {
                        ConnectingStatus.Connecting -> CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                            color = Chrono.colors.sky
                        )
                        ConnectingStatus.Disconnected,
                        ConnectingStatus.Connected -> Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = when (animatedStatus) {
                                        ConnectingStatus.Connected -> Chrono.colors.grass
                                        else -> Chrono.colors.blaze
                                    },
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
            Column {
                val contentColor = Chrono.colors.contentColorFor(containerColor)
                Text(text = "Device $id", style = Chrono.typo.label3Bold, color = contentColor)
                Text(text = status.label(), style = Chrono.typo.body2Regular, color = contentColor)
            }
        }
    }

}

@Preview
@Composable
fun PreviewDevice() = PreviewColumn {
    DeviceDetailsButton(
        modifier = Modifier.fillMaxWidth(),
        id = "123",
        active = true,
        status = DeviceDetailsButton.ConnectingStatus.Connected
    ) {}
    DeviceDetailsButton(
        modifier = Modifier.fillMaxWidth(),
        id = "123",
        active = false,
        status = DeviceDetailsButton.ConnectingStatus.Disconnected
    ) {}
    DeviceDetailsButton(
        modifier = Modifier.fillMaxWidth(),
        id = "123",
        active = false,
        status = DeviceDetailsButton.ConnectingStatus.Connecting
    ) {}

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DeviceDetailsButton(
            modifier = Modifier.weight(1F),
            id = "123",
            active = true,
            status = DeviceDetailsButton.ConnectingStatus.Connecting
        ) {}
        DeviceDetailsButton(
            modifier = Modifier.weight(1F),
            id = "456",
            active = false,
            status = DeviceDetailsButton.ConnectingStatus.Disconnected
        ) {}
    }
}