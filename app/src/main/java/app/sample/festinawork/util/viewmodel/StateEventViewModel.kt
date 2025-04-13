package app.sample.festinawork.util.viewmodel

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class StateEventViewModel<S, E> : StateViewModel<S>() {

    /**
     * There's an argument to be had around Channels not supporting multiple first subscribers.
     * https://github.com/Kotlin/kotlinx.coroutines/issues/3002
     * This should however not be a problem as in our architecture a single ViewModel tied to a single Route/Screen
     * is desired.
     * It's also only if multiple subscribers try to get the flow BEFORE transmitting
     * - which really should be a theoretical edge case at best.
     * > if we use consumeAsFlow() then event channels from different ViewModels will crash.
     * */
    private val internalEvents = Channel<E>()
    val eventsFlow: Flow<E> = internalEvents.receiveAsFlow()

    protected fun sendEvent(event: E) {
        launch { internalEvents.send(event) }
    }

    override fun onCleared() {
        Log.i("ViewModel", "Cleared ViewModel ($this)")
    }
}
