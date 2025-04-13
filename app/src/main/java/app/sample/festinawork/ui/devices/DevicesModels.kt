package app.sample.festinawork.ui.devices

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import app.sample.studio.component.DeviceDetailsButton

object DevicesModels {

    data class State(
        val loading: Boolean = true,
        val activeDeviceId: String? = null,
        val devices: List<DeviceItem> = emptyList(),
        /** Don't know how this would look yet but if the output is messy this makes it possible to format this to some extent and demonstrate some responsibility of ViewModel vs View. If it's very simple and over-engineered this will change to a string. */
        val deviceOutput: List<String> = emptyList(),
    ) {
        val activeDeviceConnected: Boolean get() = devices.firstOrNull { it.id == activeDeviceId }?.status == DeviceDetailsButton.ConnectingStatus.Connected
    }

    data class DeviceItem(
        val id: String,
        val status: DeviceDetailsButton.ConnectingStatus,
        // For a larger list, or based on a case by case situation I would put a lambda here
        // For this onClick it would make sense but I could also add `fun onDeviceClicked(id: String)` to actions.
        // Since I will be able to showcase the Actions anyway I will use this approach.
        val onClick: () -> Unit
    )

    sealed interface Event {
        data class ShowToast(val message: String) : Event
    }

    interface Actions {
        fun connectDevice(id: String, connect: Boolean)

        companion object {
            operator fun invoke() = object : Actions {
                override fun connectDevice(id: String, connect: Boolean) {}
            }
        }
    }

    val PreviewStateTwoDevices: State = State(
        // Single Device Connected - No Output
        activeDeviceId = "123",
        devices = listOf(
            DeviceItem(
                id = "123",
                status = DeviceDetailsButton.ConnectingStatus.Connected,
                onClick = {}
            ),
            DeviceItem(
                id = "456",
                status = DeviceDetailsButton.ConnectingStatus.Disconnected,
                onClick = {}
            ),
        ),
        deviceOutput = emptyList(),
    )

    val PreviewStateSingleDevice = State(
        activeDeviceId = "123",
        devices = listOf(
            DeviceItem(
                id = "123",
                status = DeviceDetailsButton.ConnectingStatus.Disconnected,
                onClick = {}
            ),
        ),
        deviceOutput = emptyList(),
    )
    private val previewStates: List<State> by lazy {
        listOf(
            State(),
            PreviewStateSingleDevice,
            PreviewStateTwoDevices
        )
    }

    class PreviewStates : CollectionPreviewParameterProvider<State>(previewStates)
}