package app.sample.data.source.mock

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

inline class FirmwareRevision(private val deviceId: Int) {

    fun version() = when (deviceId) {
        123 -> EARLY
        456 -> LATER
        else -> null
    }

    @SuppressLint("SimpleDateFormat")
    fun completeLog(): String {
        val dateFormatter = SimpleDateFormat()

        return when (version()) {
            LATER -> """
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

    companion object {
        const val EARLY = "rev_a"
        const val LATER = "rev_b"
    }
}