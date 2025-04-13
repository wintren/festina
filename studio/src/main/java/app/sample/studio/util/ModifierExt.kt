package app.sample.studio.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo

inline fun Modifier.then(modifierBlock: Modifier.() -> Modifier) = this.then(modifierBlock(Modifier))

/**
 * Applies the modifier provided if the predicate is true, uses Modifier.then()
 * Example: Modifier.thenIf(enabled) { clickable(onClick = parentOnClick) }
 */
inline fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier): Modifier {
    return when (predicate) {
        true -> this.then(block(Modifier))
        false -> this
    }
}

inline fun Modifier.thenIfPreview(crossinline block: Modifier.() -> Modifier): Modifier = composed {
    when (isPreview()) {
        true -> this.then(block(Modifier))
        false -> this
    }
}

/**
 * Applies the modifier provided if the nullable value is not null, uses Modifier.then()
 * Example: Modifier.thenCheckNull("NotNull") { clickable(onClick = handleString(it)) }
 */
fun <T : Any?> Modifier.thenCheckNull(
    nullable: T?,
    block: Modifier.(value: T) -> Modifier
) = nullable?.let { this then block.invoke(Modifier, nullable) } ?: this

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.debounceClickable(onClick: () -> Unit): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "debounceClickable"
        properties["onClick"] = onClick
    }) {
    val throttleClickListener = remember { SharedThrottleClickListener() }
    Modifier.clickable(onClick = { throttleClickListener.onClick(onClick) })
}

@Composable
fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed comp@{
    this@comp.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}
