package app.sample.festinawork

import android.annotation.SuppressLint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.random.Random

//
//  MockedDevice
//
//  Copyright © 2025 Festina Sweden AB. All rights reserved.
//

const val logPageSize = 4

class Device(
    override var id: Int,
    override var deviceListener: DeviceListener?
) : DeviceInterface {

    private var commandState: CMDState? = null
    internal var isConnected: Boolean = false
    internal var fwRevision: FirmwareRevision? = FirmwareRevision(id)

    override fun read(): String? {
        if (!isConnected) return null
        Thread.sleep((Random.nextDouble(0.04, 0.2) * 1000L).toLong())

        val cmd = commandState ?: return null

        return when (cmd) {
            is Log -> try {
                log(cmd.page)
            } catch (error: Exception) {
                disconnect(error)
                null
            }
            is Firmware -> cmd.revision.version()
            is LogSize -> if (fwRevision?.version() == later)
                ceil(
                    (fwRevision?.completeLog()?.length ?: 0) / logPageSize.toDouble()
                ).toInt().toString()
            else
                null
        }
    }

    override fun write(cmd: String, value: Int) {
        if (!isConnected) return

        when (cmd) {
            "log" -> commandState = Log(value)
            "fw_rev" -> {
                val fwRevision = FirmwareRevision(id)
                commandState = Firmware(fwRevision)
            }
            "log_size" -> {
                val fwRevision = FirmwareRevision(id)
                if (fwRevision.version() != later)
                    disconnect(InvalidCommand())
                else commandState = LogSize
            }
            else -> disconnect(InvalidCommand())
        }
    }

    override fun connect() {
        if (fwRevision == null || isConnected) return
        val device = this
        GlobalScope.launch {
            delay((Random.nextDouble(1.0, 2.0) * 1000L).toLong())
            isConnected = true
            this@Device.deviceListener?.onConnected(device)
        }
    }


    override fun disconnect() {
        if (!isConnected) return
        disconnect(null, 500)
    }
}

sealed class CMDState
data class Log(val page: Int) : CMDState()
data class Firmware(val revision: FirmwareRevision) : CMDState()
object LogSize : CMDState()

const val early = "rev_a"
const val later = "rev_b"

inline class FirmwareRevision(private val deviceId: Int) {

    fun version() = when (deviceId) {
        123 -> early
        456 -> later
        else -> null
    }

    @SuppressLint("SimpleDateFormat")
    fun completeLog(): String {
        val dateFormatter = SimpleDateFormat()

        return when (version()) {
            later -> """
Firmware revision: $deviceId
Date: ${dateFormatter.format(Date())}
- This happened today -
· I woke up
· Someone pressed a button
· I made a sound
· Someone pressed a button again
· I made a sound, again
· I stayed awake for a while
· I went to sleep 😴"""
            else -> """
Firmware revision: $deviceId
Date: ${dateFormatter.format(Date())}
-This happened today-
· I woke up
· Someone pressed a button
· I didn't care
· Someone pressed a button again
· I made a sound
· I went to sleep 😴"""
        }
    }
}

sealed class DeviceError : Exception()
class InvalidCommand : DeviceError()
class InvalidCommandArgument : DeviceError()

private fun Device.disconnect(error: Exception?, delayAmount: Long = 0) {
    val device = this
    GlobalScope.launch {
        delay(delayAmount)
        isConnected = false
        this@disconnect.deviceListener?.onDisconnected(device, error)
    }
}

private fun Device.log(page: Int): String? {
    val fw = fwRevision ?: return null

    val completeLog = fw.completeLog()
    val pageValue: String?

    val startIndex = completeLog.lastIndex.coerceAtMost(page * logPageSize)
    val endIndex = completeLog.lastIndex.coerceAtMost((page + 1) * logPageSize)
    pageValue = completeLog.substring(startIndex, endIndex)

    if (later == fw.version() && startIndex == endIndex) {
        throw InvalidCommandArgument()
    }
    if (startIndex == endIndex) return null

    return pageValue
}


