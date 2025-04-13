package app.sample.studio.util

import androidx.compose.ui.graphics.Color

fun color(hex: String): Color = Color(android.graphics.Color.parseColor(hex))
fun color(hex: String, alpha: Float): Color = Color(android.graphics.Color.parseColor(hex)).copy(alpha = alpha)
fun String.toColor(): Color = color(this)

fun Color.alpha(alpha: Float) = this.copy(alpha = alpha)
