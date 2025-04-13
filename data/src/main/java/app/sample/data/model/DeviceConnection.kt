package app.sample.data.model

import app.sample.data.source.SourceDevice
import app.sample.domain.model.DeviceStatus

data class DeviceConnection(
    val source: SourceDevice,
    val connectionStatus: DeviceStatus
) {
    val id: String get() = source.id.toString()
}
