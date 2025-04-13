package app.sample.festinawork.routes

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import app.sample.festinawork.ui.devices.DevicesModels
import app.sample.festinawork.ui.devices.DevicesScreen
import app.sample.festinawork.ui.devices.DevicesViewModel
import app.sample.festinawork.util.viewmodel.collectEvents
import app.sample.festinawork.util.viewmodel.collectState

@Composable
fun DevicesRoute(
    // Would have access to navController and the Composable Nav Builder
) {
    val viewModel: DevicesViewModel = hiltViewModel()
    val state by viewModel.collectState()

    DevicesScreen(
        state = state,
        actions = viewModel
    )

    val context = LocalContext.current
    viewModel.collectEvents { event ->
        when (event) {
            // Here would be where navigation to other screen would be handled
            is DevicesModels.Event.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}