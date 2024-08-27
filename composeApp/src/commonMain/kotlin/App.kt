import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bmibuddy.composeapp.generated.resources.Res
import bmibuddy.composeapp.generated.resources.compose_multiplatform
import di.appModule
import navgraph.BmiCalculatorApp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import theme.AppTheme

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule(true))
    }) {
        AppTheme { BmiCalculatorApp() }
    }
}