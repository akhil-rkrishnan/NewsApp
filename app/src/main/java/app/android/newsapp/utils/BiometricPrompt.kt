package app.android.newsapp.utils

import android.app.Activity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import app.android.newsapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BiometricPrompt(private val context: Activity) :
    BiometricPrompt.AuthenticationCallback() {

    var isBiometricEnabled: Boolean = false
        private set

    private val _biometricAuthFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.None)
    val biometricAuthFlow get() = _biometricAuthFlow.asStateFlow()

    private val executor by lazy {
        ContextCompat.getMainExecutor(context)
    }

    private val promptInfo by lazy {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometricTitle))
            .setDescription(context.getString(R.string.biometricDescription))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()
    }

    private val biometricPrompt by lazy {
        BiometricPrompt(context as FragmentActivity, executor, this)
    }

    private val biometricManager by lazy {
        BiometricManager.from(context)
    }

    init {
        isBiometricEnabled =
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
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
        _biometricAuthFlow.update { AuthState.BiometricError(errorString.toString()) }
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        _biometricAuthFlow.update { AuthState.BiometricSuccess }
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        _biometricAuthFlow.update { AuthState.BiometricFailed }
    }
}

sealed class AuthState {
    object None : AuthState()
    object BiometricSuccess : AuthState()
    object BiometricFailed : AuthState()
    data class BiometricError(val message: String) : AuthState()
}