package app.sample.festinawork.data


interface Device {

    /**
    The device identifier is unique for all devices.
     */
    var id: Int

    /**
    Read a value from the device.

    The data returned by a read operation is determined by the latest preceding write operation, i.e. all reads after a write operation for command A will return data produced by command A.

    - important: A read operation is synchronous. Maximum returned data size corresponds to the page size of the current device architecture.

    - returns: Data returned by the device, or null if no data is available.
     */
    fun read() : String?

    /**
    Write to the device.

    - parameters:
    - cmd: command string to write a value for
    - value: value to write to the device
     */
    fun write(cmd: String, value: Int)

    /**
    Initiate a connection to the device.
     */
    fun connect()

    /**
    Disconnect from the device.
     */
    fun disconnect()

    /**
    The object that acts as listener for the Device.

    The listener must adopt the DeviceListener interface.
     */
    var deviceListener: DeviceListener?
}

interface DeviceListener {

    /**
    Called when a connection has been established to a Device.

    - parameters:
    - device: the Device that a connection has been established for.
     */
    fun onConnected(device: Device)

    /**
    Called when a connection to a Device has ended.

    - parameters:
    - device: the Device that the connection has ended for.
    - error: Error that caused the disconnection. Null if the disconnection was caused by 'normal' circumstances.
     */
    fun onDisconnected(device: Device, error: Exception?)
}
