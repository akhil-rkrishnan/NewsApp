package app.android.newsapp.ui.screens.landing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.network.connection.NetworkConnection
import app.android.newsapp.ui.screens.landing.navigation.LandingNavGraph
import app.android.newsapp.ui.theme.BBCNewsTheme
import app.android.newsapp.ui.theme.black
import app.android.newsapp.ui.theme.lightGrey
import app.android.newsapp.utils.showToast
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Landing screen activity
 **/
@AndroidEntryPoint
class LandingActivity : ComponentActivity() {

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var networkConnection: NetworkConnection

    private val viewModel by viewModels<LandingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = black,
                    darkIcons = false
                )
            }
            BBCNewsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = lightGrey) {
                    LandingNavGraph(
                        viewModel = viewModel,
                        hasNetwork = networkConnection.isNetworkAvailable
                    )
                }
            }
        }
        // collector for global toasts
        lifecycleScope.launch {
            eventBus.toastFlow.collectLatest {
                showToast(it)
            }
        }
    }
}