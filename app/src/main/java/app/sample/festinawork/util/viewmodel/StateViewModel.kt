package app.sample.festinawork.util.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<S> : ViewModel() {
    abstract val stateFlow: MutableStateFlow<S>
    val state: S get() = stateFlow.value

    protected fun updateState(update: S.() -> S) = stateFlow.update { update(it) }
}
