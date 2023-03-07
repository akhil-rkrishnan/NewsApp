package app.android.newsapp.ui.common_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app.android.newsapp.R
import app.android.newsapp.ui.utils.fontDimensionResource

@Composable
fun SplashText(text: String) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
            fontWeight = FontWeight(500),
            fontSize = fontDimensionResource(id = R.dimen.splashTextSize)
        )
    )
}