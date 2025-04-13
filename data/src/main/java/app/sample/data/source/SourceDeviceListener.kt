package app.sample.data.source

interface SourceDeviceListener {

    /**
    Called when a connection has been established to a Device.

    - parameters:
    - device: the Device that a connection has been established for.
     */
    fun onConnected(device: SourceDevice)

    /**
    Called when a connection to a Device has ended.

    - parameters:
    - device: the Device that the connection has ended for.
    - error: Error that caused the disconnection. Null if the disconnection was caused by 'normal' circumstances.
     */
    fun onDisconnected(device: SourceDevice, error: Exception?)
}