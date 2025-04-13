package app.sample.festinawork.ui.devices

import android.util.Log
import app.sample.domain.model.DeviceInfo
import app.sample.domain.model.DeviceStatus
import app.sample.domain.result.ConnectDeviceResult
import app.sample.domain.result.DeviceListingResult
import app.sample.domain.usecase.ConnectDeviceUseCase
import app.sample.domain.usecase.DisconnectDeviceUseCase
import app.sample.domain.usecase.GetDeviceListingUseCase
import app.sample.domain.usecase.ReadDeviceLogUseCase
import app.sample.festinawork.ui.devices.DevicesModels.Actions
import app.sample.festinawork.ui.devices.DevicesModels.Event
import app.sample.festinawork.ui.devices.DevicesModels.State
import app.sample.festinawork.util.viewmodel.StateEventViewModel
import app.sample.festinawork.util.viewmodel.launch
import app.sample.studio.component.DeviceDetailsButton.ConnectingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val getDevices: GetDeviceListingUseCase,
    private val connectDevice: ConnectDeviceUseCase,
    private val readDeviceLog: ReadDeviceLogUseCase,
    private val disconnectDevice: DisconnectDeviceUseCase
) : StateEventViewModel<State, Event>(), Actions {

    override val stateFlow = MutableStateFlow(State())
    private var deviceUpdates: Job? = null
    private var readingDeviceLog: Job? = null

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
                                loading = false, deviceOutput = listOf("Error", result.message)
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
                    activeDeviceId = initialActiveDevice.id
                )
            } else copy() // Don't update activeDevice on sequential updates of the "same listing"
            // Update state that always should be updated

            initialState.copy(devices = makeDeviceItems(deviceListing))
        }
    }

    private fun makeDeviceItems(devices: List<DeviceInfo>) = devices.map { device ->
        DevicesModels.DeviceItem(
            id = device.id,
            status = when (device.status) {
                // Connecting is not part of the domain model - it's a "flow/logic state" and will be handled by the UI.
                DeviceStatus.CONNECTED -> ConnectingStatus.Connected
                DeviceStatus.DISCONNECTED -> ConnectingStatus.Disconnected
            },
            onClick = { selectActiveDevice(device) }
        )
    }

    private fun selectActiveDevice(device: DeviceInfo) {
        readingDeviceLog?.cancel()
        Log.d("DevicesViewModel", "Device Clicked: ${device.id}")
        updateState { copy(activeDeviceId = device.id, deviceOutput = emptyList()) }
        if (device.status == DeviceStatus.CONNECTED) {
            // Reconnect to change flow
            launch { connectDevice(device.id, connect = true) }
            launch { readLog(device.id) }
        }

    }

    override fun connectDevice(id: String, connect: Boolean) {
        deviceUpdates?.cancel()
        // todo, check updates when switching
        deviceUpdates = launch {
            when (state.activeDeviceConnected) {
                false -> connect(id)
                true -> disconnect(id)
            }
        }
    }

    private fun disconnect(id: String) {
        sendEvent(Event.ShowToast("Disconnecting to Device ($id) ..."))
        readingDeviceLog?.cancel()
        deviceUpdates?.cancel()
        launch {
            disconnectDevice(id).fold(
                onSuccess = {
                    Log.d("DevicesViewModel", "DISCONNECT $id")
                    updateState {
                        copy(
                            devices = devices.map { device ->
                                if (device.id == id) device.copy(status = ConnectingStatus.Disconnected)
                                else device
                            }
                        )
                    }
                },
                onFailure = { Log.d("DevicesViewModel", "DISCONNECT ERROR $id") })
        }
    }

    private suspend fun connect(id: String) {
        sendEvent(Event.ShowToast("Connecting to Device ($id) ..."))
        connectDevice(id).collect { connectResult ->
            Log.d("DevicesViewModel", "CONNECT Result $connectResult")
            when (connectResult) {
                is ConnectDeviceResult.ConnectedDevice -> onDeviceConnected(id, connectResult)
                ConnectDeviceResult.Connecting -> {
                    updateState {
                        copy(
                            devices = devices.map { device ->
                                if (device.id == id) device.copy(status = ConnectingStatus.Connecting)
                                else device
                            })
                    }
                }
                is ConnectDeviceResult.Disconnected -> {
                    updateState {
                        copy(
                            devices = devices.map { device ->
                                if (device.id == id) device.copy(status = ConnectingStatus.Disconnected)
                                else device
                            })
                    }
                    sendEvent(Event.ShowToast("Disconnected $connectResult"))
                }
                is ConnectDeviceResult.Error -> sendEvent(Event.ShowToast("Error: ${connectResult.message}"))
            }
        }
    }

    private fun onDeviceConnected(id: String, connectResult: ConnectDeviceResult.ConnectedDevice) {
        updateState {
            copy(
                devices = devices.map { device ->
                    if (device.id == id) device.copy(status = ConnectingStatus.Connected)
                    else device
                },
                deviceOutput = connectResult.device.log.split("\n")
            )
        }
        sendEvent(Event.ShowToast("ConnectedDevice $connectResult"))
        readLog(id)
    }

    private fun readLog(id: String) {
        readingDeviceLog = launch {
            readDeviceLog(id).collect { result ->
                result.fold(
                    onSuccess = {
                        updateState {
                            copy(deviceOutput = it)
                        }
                    },
                    onFailure = { error ->
                        Log.e("DevicesViewModel", "Unable to get Device Log", error)
                        sendEvent(Event.ShowToast("Error: ${error.message}"))
                    }
                )
            }
        }
    }
}