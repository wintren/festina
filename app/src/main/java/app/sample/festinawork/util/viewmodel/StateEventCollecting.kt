package app.sample.festinawork.util.viewmodel

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

@Composable
fun <S> StateViewModel<S>.collectState(): State<S> =
    stateFlow.collectAsStateWithLifecycle()

fun <S> StateViewModel<S>.collectState(
    activity: ComponentActivity,
    onState: (S) -> Unit
): Job = activity.lifecycleScope.launch {
    stateFlow.flowWithLifecycle(activity.lifecycle).collect(onState)
}

@SuppressLint("ComposableNaming")
@Composable
fun <S, E> StateEventViewModel<S, E>.collectEvents(onEvent: (event: E) -> Unit) {
    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) { eventsFlow.flowWithLifecycle(lifecycle).collect(onEvent) }
}

@Composable
inline fun <reified E> collectEvents(
    vararg viewModels: StateEventViewModel<*, E>,
    crossinline onEvent: (event: E) -> Unit
) = viewModels.toList().collectEvents(onEvent)

@Composable
inline fun <reified E> List<StateEventViewModel<*, E>>.collectEvents(
    crossinline onEvent: (event: E) -> Unit
) {
    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        map { viewModel -> viewModel.eventsFlow }
            .merge()
            .flowWithLifecycle(lifecycle)
            .collect { onEvent(it) }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <E> EventViewModel<E>.collectEvents(onEvent: (event: E) -> Unit) {
    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) { eventsFlow.flowWithLifecycle(lifecycle).collect(onEvent) }
}

fun <E> EventViewModel<E>.collectEvents(activity: ComponentActivity, onEvent: (event: E) -> Unit) {
    val lifecycle = activity.lifecycle
    launch { eventsFlow.flowWithLifecycle(lifecycle).collect(onEvent) }
}
