@file:OptIn(DelicateCoroutinesApi::class)

package app.sample.data.source.mock

import app.sample.data.source.SourceDevice
import app.sample.data.source.SourceDeviceListener
import app.sample.data.source.mock.FirmwareRevision.Companion.LATER
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.random.Random

/**
 * MockedSourceDevice
 *
 * Copyright © 2025 Festina Sweden AB. All rights reserved.
 */
class MockedSourceDevice(
    override var id: Int,
    override var deviceListener: SourceDeviceListener?
) : SourceDevice {

    private var commandState: CMDState? = null
    internal var isConnected: Boolean = false
    internal var fwRevision: FirmwareRevision? = FirmwareRevision(id)

    override fun read(): String? {
        if (!isConnected) return null
        Thread.sleep((Random.nextDouble(0.04, 0.2) * 1000L).toLong())

        val cmd = commandState ?: return null

        return when (cmd) {
            is CMDState.Log -> try {
                log(cmd.page)
            } catch (error: Exception) {
                disconnect(error)
                null
            }
            is CMDState.Firmware -> cmd.revision.version()
            is CMDState.LogSize -> if (fwRevision?.version() == LATER)
                ceil(
                    (fwRevision?.completeLog()?.length ?: 0) / LOG_PAGE_SIZE.toDouble()
                ).toInt().toString()
            else
                null
        }
    }

    override fun write(cmd: String, value: Int) {
        if (!isConnected) return

        when (cmd) {
            "log" -> commandState = CMDState.Log(value)
            "fw_rev" -> {
                val fwRevision = FirmwareRevision(id)
                commandState = CMDState.Firmware(fwRevision)
            }
            "log_size" -> {
                val fwRevision = FirmwareRevision(id)
                if (fwRevision.version() != LATER)
                    disconnect(DeviceError.InvalidCommand())
                else commandState = CMDState.LogSize
            }
            else -> disconnect(DeviceError.InvalidCommand())
        }
    }

    override fun connect() {
        if (fwRevision == null || isConnected) return
        val device = this
        GlobalScope.launch {
            delay((Random.nextDouble(1.0, 2.0) * 1000L).toLong())
            isConnected = true
            this@MockedSourceDevice.deviceListener?.onConnected(device)
        }
    }


    override fun disconnect() {
        if (!isConnected) return
        disconnect(null, 500)
    }

    companion object {
        const val LOG_PAGE_SIZE = 4
    }
}

