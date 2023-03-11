package app.android.newsapp.ui.common_components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.android.newsapp.R
import app.android.newsapp.ui.theme.blue

/**
 * Composable for splash button
 * @param modifier Modifier for the composable
 * @param onClick invoke when user click on the button
 **/
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
        ButtonText(text = stringResource(R.string.launch_latest_news))
    }
}