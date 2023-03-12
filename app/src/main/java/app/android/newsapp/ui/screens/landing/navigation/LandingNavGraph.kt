package app.android.newsapp.ui.screens.landing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.android.newsapp.ui.screens.landing.LandingViewModel
import app.android.newsapp.ui.screens.landing.NewsDetailsScreen
import app.android.newsapp.ui.screens.landing.NewsListScreen
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes.NewsDetailsRoute
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes.NewsListRoute

@Composable
fun LandingNavGraph(
    viewModel: LandingViewModel,
    hasNetwork: Boolean,
    startDestination: String = NewsListRoute,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NewsListRoute) {
            NewsListScreen(
                viewModel = viewModel,
                hasNetwork = hasNetwork,
                navController = navController
            )
        }
        composable(route = NewsDetailsRoute) {
            NewsDetailsScreen(viewModel = viewModel, navController = navController)
        }
    }
}

object LandingRoutes {
    const val NewsListRoute = "newsListRoute"
    const val NewsDetailsRoute = "newsDetailsRoute"
}