package navgraph

import bmibuddy.composeapp.generated.resources.Res
import bmibuddy.composeapp.generated.resources.bmi_calculate
import bmibuddy.composeapp.generated.resources.bmi_result
import bmibuddy.composeapp.generated.resources.desktop_platform

import org.jetbrains.compose.resources.StringResource

enum class BmiAppScreens(val title: StringResource) {
    BmiCalculateScreen(Res.string.bmi_calculate),
    BmiResultScreen(Res.string.bmi_result)
}

enum class PlatFormType(val names: StringResource) {
    Desktop(Res.string.desktop_platform)
}
