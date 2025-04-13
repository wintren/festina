package app.sample.domain.result

import app.sample.domain.model.DeviceInfo

sealed interface DeviceListingResult {
    data object NoDevices : DeviceListingResult
    data class Devices(val devices: List<DeviceInfo>) : DeviceListingResult
    data class Error(val throwable: Throwable) : DeviceListingResult {
        val message: String get() = throwable.message?.takeIf { it.isNotEmpty() } ?: "Unknown error"
    }
}