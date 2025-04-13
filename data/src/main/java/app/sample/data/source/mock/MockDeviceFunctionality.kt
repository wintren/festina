package app.sample.data.source.mock

import app.sample.data.source.mock.FirmwareRevision.Companion.LATER
import app.sample.data.source.mock.MockedSourceDevice.Companion.LOG_PAGE_SIZE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun MockedSourceDevice.disconnect(error: Exception?, delayAmount: Long = 0) {
    val device = this
    GlobalScope.launch {
        delay(delayAmount)
        isConnected = false
        this@disconnect.deviceListener?.onDisconnected(device, error)
    }
}

internal fun MockedSourceDevice.log(page: Int): String? {
    val fw = fwRevision ?: return null

    val completeLog = fw.completeLog()
    val pageValue: String?

    val startIndex = completeLog.lastIndex.coerceAtMost(page * LOG_PAGE_SIZE)
    val endIndex = completeLog.lastIndex.coerceAtMost((page + 1) * LOG_PAGE_SIZE)
    pageValue = completeLog.substring(startIndex, endIndex)

    if (LATER == fw.version() && startIndex == endIndex) {
        throw DeviceError.InvalidCommandArgument()
    }
    if (startIndex == endIndex) return null

    return pageValue
}