package app.sample.festinawork.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.sample.festinawork.ui.devices.DevicesModels
import app.sample.festinawork.ui.devices.DevicesScreen
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
    DevicesScreen(
        // TODO: ViewModel etc.
        DevicesModels.State(),
        DevicesModels.Actions()
    )
}