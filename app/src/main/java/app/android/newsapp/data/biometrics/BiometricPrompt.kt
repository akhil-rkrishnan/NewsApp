package app.android.newsapp.data.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import app.android.newsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
* Class for biometric authentication
 * @param context - Context
 **/
class BiometricPrompt(private val context: Context) :
    BiometricPrompt.AuthenticationCallback() {

    var isBiometricEnabled: Boolean = false
        private set

    private val _biometricAuthChannel = Channel<AuthState>()
    val biometricAuthFlow get() = _biometricAuthChannel.receiveAsFlow()

    private val executor by lazy {
        ContextCompat.getMainExecutor(context)
    }

    private val promptInfo by lazy {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometricTitle))
            .setDescription(context.getString(R.string.biometricDescription))
            .setNegativeButtonText(context.getString(R.string.biometricNegative))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
    }

    private val biometricPrompt by lazy {
        BiometricPrompt(context as FragmentActivity, executor, this)
    }

    private val biometricManager by lazy {
        BiometricManager.from(context)
    }

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    init {
        isBiometricEnabled =
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }
                else -> {
                    false
                }
            }
    }

    fun loadBiometrics() {
        if (isBiometricEnabled)
            biometricPrompt.authenticate(promptInfo)
    }

    override fun onAuthenticationError(errorCode: Int, errorString: CharSequence) {
        super.onAuthenticationError(errorCode, errorString)
        sendAuthState(AuthState.BiometricError(errorString.toString()))
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        sendAuthState(AuthState.BiometricSuccess)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        sendAuthState(AuthState.BiometricFailed)
    }

    private fun sendAuthState(state: AuthState) {
        coroutineScope.launch {
            _biometricAuthChannel.send(state)
        }
    }
}

sealed class AuthState {
    object None : AuthState()
    object BiometricSuccess : AuthState()
    object BiometricFailed : AuthState()
    data class BiometricError(val message: String) : AuthState()
}