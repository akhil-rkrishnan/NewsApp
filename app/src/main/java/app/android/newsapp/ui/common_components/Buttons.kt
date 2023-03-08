package app.android.newsapp.ui.common_components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.android.newsapp.ui.theme.blue

@Composable
fun SplashButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = blue
        ), onClick = onClick
    ) {
        ButtonText(text = "Let's Go!")
    }
}