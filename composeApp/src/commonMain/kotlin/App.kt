import androidx.compose.runtime.Composable
import di.appModule
import navgraph.BmiCalculatorApp
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