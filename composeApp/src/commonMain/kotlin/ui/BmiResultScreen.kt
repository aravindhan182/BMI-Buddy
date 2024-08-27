package ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bmibuddy.composeapp.generated.resources.Res
import bmibuddy.composeapp.generated.resources.undraw_functions_re_alho
import org.jetbrains.compose.resources.painterResource
import util.roundTo


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BmiResultScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    bmiValue: Float
) {
    var resultMessage by remember { mutableStateOf("") }
    var targetColor by remember { mutableStateOf(Color.Gray) }
    // Update the message and color based on the BMI value
    LaunchedEffect(bmiValue) {
        when {
            bmiValue < 16.0 -> {
                targetColor = Color.Red
                resultMessage = "Very Severely Underweight"
            }

            bmiValue in 16.0..17.0 -> {
                targetColor = Color(0xFFfa751f)
                resultMessage = "Severely Underweight"
            }

            bmiValue in 17.0..18.5 -> {
                targetColor = Color(0xFFfa9d1f)
                resultMessage = "Underweight"
            }

            bmiValue in 18.5..25.0 -> {
                targetColor = Color.Green
                resultMessage = "Normal"
            }

            bmiValue in 25.0..30.0 -> {
                targetColor = Color(0xFFfa9d1f)
                resultMessage = "Overweight"
            }

            bmiValue in 30.0..35.0 -> {
                targetColor = Color.Red
                resultMessage = "Obese Class 1"
            }

            bmiValue in 35.0..40.0 -> {
                targetColor = Color.Red
                resultMessage = "Obese Class 2"
            }

            bmiValue > 40 -> {
                targetColor = Color.Red
                resultMessage = "Obese Class 3"
            }
        }
    }

    val animatedColor by animateColorAsState(targetColor)

    Column(
        modifier = Modifier
            .fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.undraw_functions_re_alho),
            modifier = Modifier
                .size(500.dp),
            contentDescription = ""
        )
        Text(text = "BMI Result: ${bmiValue.roundTo(2)}", fontFamily = FontFamily.Serif, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.padding(4.dp))
        Text(
            text = resultMessage,
            color = animatedColor, fontFamily = FontFamily.Serif, fontSize = 16.sp, fontWeight = FontWeight.SemiBold
        )
    }
}