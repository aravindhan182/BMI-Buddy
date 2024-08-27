package ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bmibuddy.composeapp.generated.resources.Res
import bmibuddy.composeapp.generated.resources.baseline_add_circle_24
import bmibuddy.composeapp.generated.resources.baseline_remove_circle_outline_24
import kotlinx.coroutines.flow.collectLatest
import navgraph.BmiAppScreens
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import theme.colorPrimary
import theme.colorSecondaryVariant
import ui.model.Gender
import ui.model.HeightIn


@Composable
fun BmiCalculateScreen(
    navHostController: NavHostController,
    modifier: Modifier,
    bmiCalculateViewModel: BmiCalculateViewModel = koinInject(),
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(true) {
        bmiCalculateViewModel.bmiCalculateError.collectLatest {
            if (it != null) {
                snackbarHostState.showSnackbar(
                    message = it, actionLabel = "ok"
                )
            }
        }
    }
    LaunchedEffect(true) {
        bmiCalculateViewModel.bmiCalculatedValue.collectLatest { bmiValue ->
            if (bmiValue != null) {
                val value = bmiValue.toFloat()
                navHostController.navigate("${BmiAppScreens.BmiResultScreen.name}/${value}")
            }
        }
    }

    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        GenderCadView(viewModel = bmiCalculateViewModel)
        Spacer(Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AgeCardView(
                title = "Age",
                viewModel = bmiCalculateViewModel
            )
            Spacer(Modifier.padding(4.dp))
            WeightCardView(
                title = "Weight(kg)",
                viewModel = bmiCalculateViewModel
            )
        }
        Spacer(Modifier.padding(16.dp))
        HeightCardView(viewModel = bmiCalculateViewModel)
        CalculateButton(viewModel = bmiCalculateViewModel)
    }
}

@Composable
fun AgeCardView(
    title: String,
    viewModel: BmiCalculateViewModel
) {
    var countOfAge by remember { mutableStateOf(0) }
    Card(
        shape =
        RoundedCornerShape(
            topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp, bottomStart = 8.dp
        ),
        backgroundColor = colorPrimary,
        modifier = Modifier.wrapContentSize(),
        elevation = 8.dp
    ) {
        Column(
            Modifier.wrapContentSize()
                .padding(start = 8.dp, end = 8.dp, top = 32.dp, bottom = 16.dp).pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount < 0) {
                            countOfAge++
                            viewModel.setAge(countOfAge)
                        } else {
                            countOfAge--
                            viewModel.setAge(countOfAge)
                        }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
            Spacer(Modifier.padding(4.dp))
            Text("${countOfAge}", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(Modifier.padding(8.dp))
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {

                IconButton(
                    onClick = {
                        countOfAge--
                        viewModel.setAge(countOfAge)
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_remove_circle_outline_24),
                        modifier = Modifier.size(42.dp),
                        contentDescription = "reduceButton"
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                IconButton(
                    onClick = {
                        countOfAge++
                        viewModel.setAge(countOfAge)
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_add_circle_24),
                        modifier = Modifier.size(42.dp),
                        contentDescription = "IncreaseButton"
                    )
                }
            }
        }
    }
}

@Composable
fun WeightCardView(
    title: String,
    viewModel: BmiCalculateViewModel
) {
    var countOfWeight by remember { mutableStateOf(0) }
    Card(
        shape =
        RoundedCornerShape(
            topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp, bottomStart = 8.dp
        ),
        backgroundColor = colorPrimary,
        modifier = Modifier.wrapContentSize(),
        elevation = 8.dp
    ) {
        Column(
            Modifier.wrapContentSize()
                .padding(start = 8.dp, end = 8.dp, top = 32.dp, bottom = 16.dp).pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount < 0) {
                            countOfWeight++
                            viewModel.setWeight(countOfWeight)
                        } else {
                            countOfWeight--
                            viewModel.setWeight(countOfWeight)
                        }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
            Spacer(Modifier.padding(4.dp))
            Text("${countOfWeight}", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(Modifier.padding(8.dp))
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {

                IconButton(
                    onClick = {
                        countOfWeight--
                        viewModel.setWeight(countOfWeight)
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_remove_circle_outline_24),
                        modifier = Modifier.size(42.dp),
                        contentDescription = "reduceButton"
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                IconButton(
                    onClick = {
                        countOfWeight++
                        viewModel.setWeight(countOfWeight)
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_add_circle_24),
                        modifier = Modifier.size(42.dp),
                        contentDescription = "IncreaseButton"
                    )
                }
            }
        }
    }
}

@Composable
fun GenderCadView(viewModel: BmiCalculateViewModel) {
    Column(
        Modifier.wrapContentSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("I'm a", fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
        Spacer(Modifier.padding(8.dp))
        val bmiView by viewModel.bmiCalculateMutableView.collectAsState()
        TextSwitch(
            selectedGender = bmiView.gender,
            items = listOf(Gender.Man, Gender.Women),
            onSelectionChange = { gender ->
                viewModel.updateGender(gender)
            }
        )
    }
}

@Composable
fun HeightCardView(viewModel: BmiCalculateViewModel) {
    Column(
        Modifier.wrapContentSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bmiView by viewModel.bmiCalculateMutableView.collectAsState()
        Text("Height in", fontWeight = FontWeight.SemiBold, fontSize = 24.sp)

        Column {
            HeightTextSwitch(
                selectedHeight = bmiView.heightIn,
                items = listOf(HeightIn.Feet, HeightIn.Centimeter),
                onSelectionChange = {
                    viewModel.setHeightIn(it)
                },
                viewModel = viewModel
            )

        }
        Spacer(Modifier.padding(8.dp))
        if (bmiView.heightIn == HeightIn.Centimeter) {
            HeightInCmWithUnderline(viewModel)
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HeightInFtWithUnderline(viewModel)
                Text("`", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(16.dp))
                HeightInInchWithUnderline(
                    viewModel
                )
                Text("``", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}


@Composable
private fun HeightTextSwitch(
    modifier: Modifier = Modifier,
    selectedHeight: HeightIn,
    items: List<HeightIn>,
    onSelectionChange: (HeightIn) -> Unit,
    viewModel: BmiCalculateViewModel
) {
    val selectedIndex = items.indexOf(selectedHeight)
    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorPrimary)
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // This is for shadow layer matching white background
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight().background(Color(0xFF708871))
            )

            Row(modifier = Modifier
                .fillMaxWidth()

                .drawWithContent {

                    // This is for setting black tex while drawing on white background
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.Black,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Color.White,
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }

                }
            ) {
                items.forEachIndexed { index, heightIn ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(heightIn)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = heightIn.value,
                            fontSize = 20.sp
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    items: List<Gender>,
    onSelectionChange: (Gender) -> Unit
) {
    val selectedIndex = items.indexOf(selectedGender)

    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorPrimary)
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
                    .background(Color(0xFF708871))
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.Black,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Color.White,
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }
                }
            ) {
                items.forEachIndexed { index, gender ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(gender)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = gender.value,
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CalculateButton(viewModel: BmiCalculateViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                viewModel.calculateTheBmi()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorSecondaryVariant,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calculate", fontSize = 28.sp)
        }
    }
}

@Composable
fun HeightInCmWithUnderline(
    viewModel: BmiCalculateViewModel,
) {
    Box(modifier = Modifier.width(100.dp)) {
        BasicTextField(
            value = viewModel.bmiCalculateMutableView.value.heightInCm,
            onValueChange = { viewModel.heightCm(it) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(bottom = 4.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp
            )
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun HeightInFtWithUnderline(
    viewModel: BmiCalculateViewModel
) {
    Box(modifier = Modifier.width(100.dp)) {
        BasicTextField(
            value = viewModel.bmiCalculateMutableView.value.heightFt ?: "",
            onValueChange = { viewModel.heightFt(it) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(bottom = 4.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp
            )
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun HeightInInchWithUnderline(
    viewModel: BmiCalculateViewModel
) {
    Box(modifier = Modifier.width(100.dp)) {
        BasicTextField(
            value = viewModel.bmiCalculateMutableView.value.heightInch ?: "",
            onValueChange = { viewModel.heightInch(it) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(bottom = 4.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp
            )
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}