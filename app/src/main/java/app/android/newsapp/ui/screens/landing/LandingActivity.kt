package app.android.newsapp.ui.screens.landing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import app.android.newsapp.ui.theme.BBCNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : ComponentActivity() {
    private val viewModel by viewModels<LandingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBCNewsTheme {
                NewsListScreen(viewModel)
            }
        }
    }
}