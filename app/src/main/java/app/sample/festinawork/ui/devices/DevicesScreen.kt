package app.sample.festinawork.ui.devices

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.CircularProgressIndicator
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
        val scaffoldPadding = Modifier.padding(innerPadding)
        when (state.loading) {
            true -> Box(
                modifier = scaffoldPadding.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            false -> DevicesContent(scaffoldPadding, state, actions)
        }
    }
}

@Composable
private fun DevicesContent(
    modifier: Modifier = Modifier,
    state: DevicesModels.State,
    actions: DevicesModels.Actions
) {
    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = state.devices.isNotEmpty()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.devices.forEach { device ->
                    DeviceDetailsButton(
                        modifier = Modifier.weight(1F),
                        // This (active: Boolean) could also be part of the model from the ViewModel
                        //  I am trying to mix some conventions to show different usages
                        active = state.activeDeviceId == device.id,
                        id = device.id,
                        status = device.status,
                        onClick = device.onClick
                    )
                }
            }
        }

        DeviceOutput(
            modifier = Modifier.weight(1f),
            id = state.activeDeviceId ?: "-",
            connected = state.activeDeviceConnected,
            output = state.deviceOutput
        )

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = state.activeDeviceId != null
        ) {
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
fun DeviceOutput(
    connected: Boolean,
    id: String,
    output: List<String>, modifier: Modifier = Modifier
) {
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
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "Device Output ($id)",
                    style = Chrono.typo.label3Bold
                )
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
            Column(
                modifier = outputModifier,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Device ($id)",
                    style = Chrono.typo.label1
                )
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
