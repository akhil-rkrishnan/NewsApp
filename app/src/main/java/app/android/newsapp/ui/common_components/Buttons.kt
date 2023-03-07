package app.android.newsapp.ui.common_components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.android.newsapp.ui.theme.googleBlue

@Composable
fun SplashButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = googleBlue
        ), onClick = onClick
    ) {
        ButtonText(text = "Let's Go!")
    }
}