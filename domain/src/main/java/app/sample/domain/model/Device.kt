package app.sample.domain.model

data class Device(
    val id: String,
    val status: DeviceStatus,
    val log: String,
    // last command
)