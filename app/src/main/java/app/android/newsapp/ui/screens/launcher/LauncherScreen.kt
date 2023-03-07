package app.android.newsapp.ui.screens.launcher

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.android.newsapp.R
import app.android.newsapp.ui.common_components.SplashText
@Preview
@Composable
fun LauncherScreen() {
    Column() {
        SplashText(text = stringResource(id = R.string.app_name))
    }
}