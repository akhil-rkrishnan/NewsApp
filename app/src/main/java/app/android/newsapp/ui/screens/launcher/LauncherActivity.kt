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
import app.android.newsapp.ui.screens.landing.LandingActivity
import app.android.newsapp.ui.theme.BBCNewsTheme
import app.android.newsapp.ui.utils.startComponentActivity
import app.android.newsapp.utils.AuthState
import app.android.newsapp.utils.BiometricPrompt
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "LauncherActivity"

class LauncherActivity : FragmentActivity() {

    private val biometrics by lazy {
        BiometricPrompt(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBCNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LauncherScreen(onClick = {
                        Log.e(TAG, "Biometrics enabled: ${biometrics.isBiometricEnabled}")
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