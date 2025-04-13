package app.sample.data.source

import app.sample.data.source.mock.MockedSourceDevice

internal fun scanForDevices() = listOf(
        MockedSourceDevice(
            123,
    //                        object : SourceDeviceListener {
    //                        override fun onConnected(device: SourceDevice) {
    //                            Log.d("DEBUG", "CONNECTED (${device.id})")
    //                        }
    //
    //                        override fun onDisconnected(device: SourceDevice, error: Exception?) {
    //                            Log.d("DEBUG", "DISCONNECTED (${device.id})")
    //                        }
    //                    }
            null
        ),
        MockedSourceDevice(456, null),
    )