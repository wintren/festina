package app.sample.studio.util

import java.time.Instant

// dictionary thought
// https://web.archive.org/web/20180324022838/http://demo.nimius.net/debounce_throttle/
//  Debounce means to perform an action if input hasn't changed for some amount of time
//  Throttle means that new input doesn't perform a new action if the last action was too recent.

class SharedThrottleClickListener {
    private var lastEventTime: Long = 0
    fun onClick(action: () -> Unit) {
        val now = Instant.now().toEpochMilli()
        if (now - lastEventTime >= DEFAULT_DEBOUNCE_TIME) {
            action.invoke()
            lastEventTime = now
        }
    }

    companion object {
        const val DEFAULT_DEBOUNCE_TIME = 500L
    }
}

class ThrottleClickListener(private val action: () -> Unit) {
    private var lastEventTime: Long = 0
    fun onClick() {
        val now = Instant.now().toEpochMilli()
        if (now - lastEventTime >= DEFAULT_DEBOUNCE_TIME) {
            action.invoke()
            lastEventTime = now
        }
    }

    companion object {
        const val DEFAULT_DEBOUNCE_TIME = 500L
    }
}
