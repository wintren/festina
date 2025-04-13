package app.sample.festinawork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.festinawork.ui.App
import app.sample.studio.Chrono
import app.sample.studio.theme.ChronoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(20.dp)
            .background(Chrono.Colors.primary, shape = Chrono.Shapes.large)
            .padding(16.dp),
        text = "Hello $name!",
        style = Chrono.Typo.headline2Bold,
        color = Chrono.colors.onPrimary
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChronoTheme {
        Greeting("Android")
    }
}