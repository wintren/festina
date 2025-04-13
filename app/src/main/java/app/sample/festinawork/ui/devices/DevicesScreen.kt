package app.sample.festinawork.ui.devices

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.studio.Chrono
import app.sample.studio.component.ChronoButton
import app.sample.studio.component.DeviceDetailsButton
import app.sample.studio.util.PreviewBox

@Composable
fun DevicesScreen(
    state: DevicesModels.State,
    actions: DevicesModels.Actions
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.devices.forEach { device ->
                    DeviceDetailsButton(
                        modifier = Modifier.weight(1F),
                        id = device.id,
                        status = device.status,
                        onClick = device.onClick
                    )
                }
            }

            DeviceOutput(
                modifier = Modifier.weight(1f),
                connected = state.activeDeviceConnected,
                output = state.deviceOutput
            )

            ChronoButton(
                modifier = Modifier.fillMaxWidth(),
                text = when (state.activeDeviceConnected) {
                    true -> "Disconnect"
                    false -> "Connect"
                },
            ) { actions.connectDevice(!state.activeDeviceConnected) }
        }
    }
}

@Composable
fun DeviceOutput(connected: Boolean, output: List<String>, modifier: Modifier = Modifier) {
    val outputModifier = modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = when (connected) {
                true -> Chrono.colors.outline
                false -> Chrono.colors.outlineVariant
            },
            shape = Chrono.shapes.large
        )
    when (connected) {
        true -> {
            Column(
                modifier = outputModifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.padding(16.dp))
                when (output.isNotEmpty()) {
                    true -> output.forEach { outputLine ->
                        Text(
                            text = outputLine,
                            style = Chrono.typo.body1Medium
                        )
                    }
                    false -> Text(modifier = Modifier.fillMaxWidth(), text = "No Output", textAlign = TextAlign.Center)
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
        false -> {
            Box(
                modifier = outputModifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Not Connected",
                    style = Chrono.typo.label3Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun DevicesScreenPreview(
    // This doesn't want to work - although it should.. So skipping it to not spend time on it.
//    @PreviewParameter(DevicesModels.PreviewStates::class) state: DevicesModels.State
) = PreviewBox(addPadding = false) {
    DevicesScreen(DevicesModels.PreviewStateTwoDevices, DevicesModels.Actions())
}
