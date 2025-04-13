package app.sample.data.source.mock

sealed class CMDState {
    data class Log(val page: Int) : CMDState()
    data class Firmware(val revision: FirmwareRevision) : CMDState()
    data object LogSize : CMDState()
}