package app.sample.domain.result

import app.sample.domain.model.Device

sealed interface ConnectDeviceResult {
    data object Connecting : ConnectDeviceResult
    data class Disconnected(val id: String) : ConnectDeviceResult
    data class ConnectedDevice(val device: Device) : ConnectDeviceResult
    data class Error(val id: String, val throwable: Throwable) : ConnectDeviceResult {
        val message: String get() = throwable.message ?: "Unknown error"
    }
}