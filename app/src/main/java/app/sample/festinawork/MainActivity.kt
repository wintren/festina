package app.sample.festinawork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sample.festinawork.ui.theme.FestinaSampleTheme
import app.sample.studio.Chrono
import app.sample.studio.theme.ChronoTheme
import app.sample.studio.util.color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChronoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
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
    FestinaSampleTheme {
        Greeting("Android")
    }
}