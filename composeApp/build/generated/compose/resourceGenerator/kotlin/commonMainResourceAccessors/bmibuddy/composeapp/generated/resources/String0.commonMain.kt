@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package bmibuddy.composeapp.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.StringResource

private object CommonMainString0 {
  public val app_name: StringResource by 
      lazy { init_app_name() }

  public val bmi_calculate: StringResource by 
      lazy { init_bmi_calculate() }

  public val bmi_result: StringResource by 
      lazy { init_bmi_result() }

  public val desktop_platform: StringResource by 
      lazy { init_desktop_platform() }
}

internal val Res.string.app_name: StringResource
  get() = CommonMainString0.app_name

private fun init_app_name(): StringResource = org.jetbrains.compose.resources.StringResource(
  "string:app_name", "app_name",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/bmibuddy.composeapp.generated.resources/values/strings.commonMain.cvr", 10,
    28),
    )
)

internal val Res.string.bmi_calculate: StringResource
  get() = CommonMainString0.bmi_calculate

private fun init_bmi_calculate(): StringResource = org.jetbrains.compose.resources.StringResource(
  "string:bmi_calculate", "bmi_calculate",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/bmibuddy.composeapp.generated.resources/values/strings.commonMain.cvr", 39,
    41),
    )
)

internal val Res.string.bmi_result: StringResource
  get() = CommonMainString0.bmi_result

private fun init_bmi_result(): StringResource = org.jetbrains.compose.resources.StringResource(
  "string:bmi_result", "bmi_result",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/bmibuddy.composeapp.generated.resources/values/strings.commonMain.cvr", 81,
    34),
    )
)

internal val Res.string.desktop_platform: StringResource
  get() = CommonMainString0.desktop_platform

private fun init_desktop_platform(): StringResource =
    org.jetbrains.compose.resources.StringResource(
  "string:desktop_platform", "desktop_platform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/bmibuddy.composeapp.generated.resources/values/strings.commonMain.cvr", 116,
    36),
    )
)
