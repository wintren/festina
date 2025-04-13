package app.sample.festinawork.ui.devices

import android.util.Log
import app.sample.domain.model.DeviceInfo
import app.sample.domain.model.DeviceStatus
import app.sample.domain.result.ConnectDeviceResult
import app.sample.domain.result.DeviceListingResult
import app.sample.domain.usecase.ConnectDeviceUseCase
import app.sample.domain.usecase.DisconnectDeviceUseCase
import app.sample.domain.usecase.GetDeviceListingUseCase
import app.sample.festinawork.ui.devices.DevicesModels.Actions
import app.sample.festinawork.ui.devices.DevicesModels.Event
import app.sample.festinawork.ui.devices.DevicesModels.State
import app.sample.festinawork.util.viewmodel.StateEventViewModel
import app.sample.festinawork.util.viewmodel.launch
import app.sample.studio.component.DeviceDetailsButton.ConnectingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val getDevices: GetDeviceListingUseCase,
    private val connectDevice: ConnectDeviceUseCase,
    private val disconnectDevice: DisconnectDeviceUseCase
) : StateEventViewModel<State, Event>(), Actions {

    override val stateFlow = MutableStateFlow(State())

    init {
        launch {
            delay(500)
            getDevices().collect { result ->
                when (result) {
                    is DeviceListingResult.Devices -> {
                        Log.i("DevicesViewModel", "Listing Devices")
                        updateState { copy(loading = false) }
                        updateState(result.devices)
                        // Delay: Simple way to show animation in UI (silly but neat)
                        delay(200)
                    }
                    DeviceListingResult.NoDevices -> {
                        Log.i("DevicesViewModel", "Devices Missing")
                        updateState { State(loading = false) }
                    }
                    is DeviceListingResult.Error -> {
                        Log.e("DevicesViewModel", "Unable to get Device Listing", result.throwable)
                        sendEvent(Event.ShowToast("Error: ${result.message}"))
                        updateState {
                            State(
                                loading = false,
                                deviceOutput = listOf("Error", result.message)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateState(deviceListing: List<DeviceInfo>) {
        updateState {
            val initialActiveDevice = deviceListing.first()
            val newDeviceIds = deviceListing.map { it.id }

            // Update Initial State if needed
            val initialState = if (state.devices.isEmpty() || state.activeDeviceId !in newDeviceIds) {
                // If the previous state was no devices, or we've lost the active Device, set the first listed device as active
                copy(
                    activeDeviceId = initialActiveDevice.id,
                    activeDeviceConnected = initialActiveDevice.status == DeviceStatus.CONNECTED
                )
            } else copy() // Don't update activeDevice on sequential updates of the "same listing"
            // Update state that always should be updated

            initialState.copy(devices = makeDeviceItems(deviceListing))
        }
    }

    private fun makeDeviceItems(devices: List<DeviceInfo>) = devices
        .map { device ->
            DevicesModels.DeviceItem(
                id = device.id,
                status = when (device.status) {
                    // Connecting is not part of the domain model - it's a "flow/logic state" and will be handled by the UI.
                    DeviceStatus.CONNECTED -> ConnectingStatus.Connected
                    DeviceStatus.DISCONNECTED -> ConnectingStatus.Disconnected
                },
                onClick = {
                    Log.d("DevicesViewModel", "Device Clicked: ${device.id}")
                    updateState { copy(activeDeviceId = device.id) }
                }
            )
        }

    override fun connectDevice(connect: Boolean) {
        // Here I throw an error - which is not pleasant for users.
        // But really good for developers I would say, the app wouldn't work as expected anyway
        // and cause weird issues - so then it's ok to crash and fix the bugs quickly!
        val id = state.activeDeviceId ?: error("Device must be not null here")
        when (state.activeDeviceConnected) {
            false -> {
                sendEvent(Event.ShowToast("Connecting to Device ($id) ..."))
                launch {
                    connectDevice(id).collect {
                        when (it) {
                            is ConnectDeviceResult.ConnectedDevice -> sendEvent(Event.ShowToast("ConnectedDevice"))
                            ConnectDeviceResult.Connecting -> sendEvent(Event.ShowToast("Connecting"))
                            is ConnectDeviceResult.Disconnected -> sendEvent(Event.ShowToast("Disconnected"))
                            is ConnectDeviceResult.Error -> sendEvent(Event.ShowToast("Error: ${it.message}"))
                        }
                    }
                }
            }
            true -> {
                sendEvent(Event.ShowToast("Disconnecting to Device ($id) ..."))
                disconnectDevice(id)
            }
        }
    }
}