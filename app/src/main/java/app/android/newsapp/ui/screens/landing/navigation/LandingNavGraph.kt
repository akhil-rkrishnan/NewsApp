package app.android.newsapp.ui.screens.landing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.android.newsapp.ui.screens.landing.LandingViewModel
import app.android.newsapp.ui.screens.landing.NewsDetailsScreen
import app.android.newsapp.ui.screens.landing.NewsListScreen
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes.NewsDetails
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes.NewsList

@Composable
fun LandingNavGraph(
    viewModel: LandingViewModel,
    hasNetwork: Boolean,
    startDestination: String = NewsList,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NewsList) {
            NewsListScreen(
                viewModel = viewModel,
                hasNetwork = hasNetwork,
                navController = navController
            )
        }
        composable(route = NewsDetails) {
            NewsDetailsScreen(viewModel = viewModel, navController = navController)
        }
    }
}

object LandingRoutes {
    const val NewsList = "newsListRoute"
    const val NewsDetails = "newsDetailsRoute"
}