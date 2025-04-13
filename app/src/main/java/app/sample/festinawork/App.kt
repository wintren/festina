package app.sample.festinawork

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import app.sample.festinawork.ui.devices.DevicesModels
import app.sample.festinawork.ui.devices.DevicesScreen
import app.sample.festinawork.ui.devices.DevicesViewModel
import app.sample.festinawork.util.viewmodel.collectEvents
import app.sample.festinawork.util.viewmodel.collectState
import app.sample.studio.theme.ChronoTheme

// I am bundling all these functions in this file
// In a proper project these would of course be much larger and deserve their own files/packages

@Composable
fun App(modifier: Modifier = Modifier) {
    // App would be the bottom setup for the app UI
    ChronoTheme {
        Navigation()
    }
}

@Composable
fun Navigation() {
    // Navigation would hold the NavLibrary and probably have some utility like printing the routes etc.
    // Here I would define all of the graphs and routes - but in this case it's just one
    DevicesRoute()
}

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