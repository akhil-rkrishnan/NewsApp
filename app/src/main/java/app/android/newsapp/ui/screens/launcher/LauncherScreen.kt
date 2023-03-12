package app.android.newsapp.ui.screens.launcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import app.android.newsapp.R
import app.android.newsapp.ui.common_components.SplashButton
import app.android.newsapp.ui.common_components.SplashText
import app.android.newsapp.ui.common_components.VerticalSpacer

/**
 * Composable for launcher screen
 * @param onClick invoked when user click on the button
 **/
@Composable
fun LauncherScreen(onClick: () -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashText(text = stringResource(id = R.string.app_name))
        VerticalSpacer(space = dimensionResource(id = R.dimen.dp50))
        SplashButton(onClick = onClick)
    }
}