package navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import getPlatform
import org.jetbrains.compose.resources.stringResource
import theme.colorPrimaryVariant
import ui.BmiCalculateScreen
import ui.BmiResultScreen


@Composable
fun BmiCalculatorAppBar(
    currentScreen: BmiAppScreens,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val canNavigateBack =
        when (currentScreen) {
            BmiAppScreens.BmiCalculateScreen -> false
            else -> true
        }
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon =
        if (canNavigateBack) {
            {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                    )
                }
            }
        } else null,
        backgroundColor = colorPrimaryVariant,
        contentColor = Color.White)
}

@Composable
fun BmiCalculatorApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        BmiAppScreens.valueOf(
            backStackEntry?.destination?.route?.split("/")?.first()
                ?: BmiAppScreens.BmiCalculateScreen.name
        )
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            BmiCalculatorAppBar(
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() })
        },

        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BmiAppScreens.BmiCalculateScreen.name,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
            }) {
            composable(route = BmiAppScreens.BmiCalculateScreen.name) {
                 BmiCalculateScreen(navController, modifier = responsivePadding(),snackbarHostState =snackbarHostState)
            }
            composable(route = "${BmiAppScreens.BmiResultScreen.name}/{bmiValue}",
                arguments = listOf(navArgument("bmiValue") { type = NavType.FloatType })) { navBackStackEntry
                ->
                val bmiValue = navBackStackEntry.arguments?.getFloat("bmiValue")!!
                BmiResultScreen(navController, modifier = responsivePadding(),bmiValue)
            }

        }
    }
}

fun isDesktop(): Boolean {
    val platform = getPlatform()
    return when (platform.name) {
        PlatFormType.Desktop.name -> true
        else -> false
    }
}


    @Composable
    fun responsivePadding(): Modifier {
        val horizontalPadding = if (isDesktop()) 200.dp else 16.dp
        val verticalPadding = 16.dp
        return Modifier.fillMaxSize().padding(horizontal = horizontalPadding, vertical = verticalPadding)
    }
