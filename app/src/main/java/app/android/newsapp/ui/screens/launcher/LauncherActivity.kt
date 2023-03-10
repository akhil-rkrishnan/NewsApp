package app.android.newsapp.ui.screens.launcher

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import app.android.newsapp.data.biometrics.AuthState
import app.android.newsapp.data.biometrics.BiometricPrompt
import app.android.newsapp.ui.screens.landing.LandingActivity
import app.android.newsapp.ui.theme.CriticalNewsTheme
import app.android.newsapp.ui.utils.startComponentActivity
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "LauncherActivity"

/**
 * Launcher Activity class
 **/
class LauncherActivity : FragmentActivity() {

    // variable to hold biometrics
    private val biometrics by lazy {
        BiometricPrompt(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriticalNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LauncherScreen(onClick = {
                        if (biometrics.isBiometricEnabled) {
                            biometrics.loadBiometrics()
                        } else {
                            startComponentActivity(LandingActivity::class.java)
                        }
                    })
                }
            }

            lifecycleScope.launchWhenResumed {
                biometrics.biometricAuthFlow.collectLatest { state ->
                    Log.e(TAG, "Biometrics enabled: $state")
                    if (state is AuthState.BiometricSuccess) {
                        startComponentActivity(LandingActivity::class.java)
                    }
                }
            }
        }
    }
}