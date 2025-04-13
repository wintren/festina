package app.sample.data.source

/**
 * This has been renamed for a couple of reasons
 * - I want domain to have the simplistic, direct naming convention reserved. Such as Device, User, Profile, Session etc.
 * - I prefer to have interfaces express exactly what they are and provide, and not indicate that they are an interface - that's somewhat redundant and creates some weird naming.
 */
interface SourceDevice {

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
    var deviceListener: SourceDeviceListener?
}


