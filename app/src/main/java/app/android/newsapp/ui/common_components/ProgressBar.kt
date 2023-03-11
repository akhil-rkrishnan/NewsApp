package app.android.newsapp.ui.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import app.android.newsapp.R
import app.android.newsapp.ui.theme.black
import app.android.newsapp.ui.utils.fontDimensionResource

/**
 * Progress bar composable
 * @param modifier modifier for the composable
 * @param color color for the progress loader
 */
@Composable
fun LinearProgressbar(modifier: Modifier = Modifier, color: Color = black, loadingLabel: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp8)),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        LinearProgressIndicator(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.dp16)),
            color = color,
        )
        Text(
            text = loadingLabel, style = LocalTextStyle.current.copy(
                color = black,
                fontSize = fontDimensionResource(id = R.dimen.sp16),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
        )
    }
}