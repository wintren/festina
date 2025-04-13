package app.sample.studio.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.sample.studio.Chrono

object ChronoButton {

    @Composable
    operator fun invoke(
        text: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        Button(
            modifier = modifier,
            onClick = onClick
        ) {
            Text(text = text, style = Chrono.typo.label2Medium)
        }
    }

}