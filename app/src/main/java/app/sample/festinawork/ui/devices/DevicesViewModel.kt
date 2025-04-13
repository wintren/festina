package app.sample.festinawork.ui.devices

import android.util.Log
import app.sample.festinawork.ui.devices.DevicesModels.Actions
import app.sample.festinawork.ui.devices.DevicesModels.Event
import app.sample.festinawork.ui.devices.DevicesModels.State
import app.sample.festinawork.util.viewmodel.StateEventViewModel
import app.sample.festinawork.util.viewmodel.launch
import app.sample.studio.component.DeviceDetailsButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor() : StateEventViewModel<State, Event>(), Actions {

    override val stateFlow = MutableStateFlow(State())
    private var initialActiveDeviceSet: Boolean = false

    init {
        // todo Initialise screen from Repo or Use Case (Domain)
        launch {
            delay(1000)
            updateState { copy(loading = false) }
            val deviceListing = listOf(
                MockDomainDevice(id = "123", connected = false, connecting = false),
                MockDomainDevice(id = "456", connected = false, connecting = false)
            )
            delay(200)
            // Delay to animate UI essentially

            updateState(deviceListing)
        }
    }

    private fun updateState(deviceListing: List<MockDomainDevice>) {
        updateState {
            val initialActiveDevice = deviceListing.first()
            copy(
                activeDeviceId = if (!initialActiveDeviceSet) initialActiveDevice.id else activeDeviceId,
                activeDeviceConnected = if (!initialActiveDeviceSet) initialActiveDevice.connected else activeDeviceConnected,
                devices = makeDeviceItems(deviceListing),
                deviceOutput = emptyList() // wip: This will not be set by the listing
                // Mock until we get domain data
            )
        }
        initialActiveDeviceSet = true
    }

    private fun makeDeviceItems(devices: List<MockDomainDevice>) = devices

        .map { device ->
            DevicesModels.DeviceItem(
                id = device.id,
                status = when (device.connected) {
                    true -> DeviceDetailsButton.ConnectingStatus.Connected
                    false -> DeviceDetailsButton.ConnectingStatus.Disconnected
                },
                onClick = {
                    Log.d("DevicesViewModel", "Device Clicked: ${device.id}")
                    updateState { copy(activeDeviceId = device.id) }
                }
            )
        }

    data class MockDomainDevice(
        val id: String,
        val connected: Boolean,
        val connecting: Boolean,
    )

    override fun connectDevice(connect: Boolean) {
        // Here I throw an error - which is not pleasant for users.
        // But really good for developers I would say, the app wouldn't work as expected anyway
        // and cause weird issues - so then it's ok to crash and fix the bugs quickly!
        val id = state.activeDeviceId ?: error("Device must be not null here")
        when (state.activeDeviceConnected) {
            false -> {
                sendEvent(Event.ShowToast("Connecting to Device ($id) ..."))
                // TODO: Domain logic
            }
            true -> {
                sendEvent(Event.ShowToast("Disconnecting to Device ($id) ..."))
                // TODO: Domain logic
            }
        }
    }
}