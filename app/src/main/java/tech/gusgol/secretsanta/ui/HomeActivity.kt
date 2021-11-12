package tech.gusgol.secretsanta.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tech.gusgol.secretsanta.ui.theme.SecretSantaTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecretSantaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: HomeViewModel = viewModel()) {
    val list = viewModel.shuffle()
    Text(text = list)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SecretSantaTheme {
        Greeting()
    }
}