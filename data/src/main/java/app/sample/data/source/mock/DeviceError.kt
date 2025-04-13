package app.sample.data.source.mock
sealed class DeviceError : Exception() {
    class InvalidCommand : DeviceError()
    class InvalidCommandArgument : DeviceError()
}