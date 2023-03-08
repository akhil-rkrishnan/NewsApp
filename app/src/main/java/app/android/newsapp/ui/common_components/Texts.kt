package app.android.newsapp.ui.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import app.android.newsapp.R
import app.android.newsapp.ui.theme.black
import app.android.newsapp.ui.utils.fontDimensionResource

@Composable
fun SplashText(text: String) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
            fontWeight = FontWeight(500),
            fontSize = fontDimensionResource(id = R.dimen.sp24),
            color = Color.Black
        )
    )
}

@Composable
fun ButtonText(text: String) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            fontWeight = FontWeight(300),
            fontSize = fontDimensionResource(id = R.dimen.sp24),
            color = Color.White
        )
    )
}

@Composable
fun TitleText(
    text: String,
    textSize: TextUnit = fontDimensionResource(id = R.dimen.sp24),
    color: Color = black
) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
            fontWeight = FontWeight(600),
            fontSize = textSize,
            color = color
        )
    )
}

@Composable
fun NewsText(
    text: String,
    textSize: TextUnit = fontDimensionResource(id = R.dimen.sp14),
    color: Color = black
) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight(300),
            fontSize = textSize,
            color = color,
        ), maxLines = 2, overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun NewsTextLight(
    text: String,
    textSize: TextUnit = fontDimensionResource(id = R.dimen.sp14),
    color: Color = black
) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_extalight)),
            fontWeight = FontWeight(300),
            fontSize = textSize,
            color = color
        ), modifier = Modifier.padding()
    )
}

@Composable
fun NewsErrorText(
    text: String,
    textSize: TextUnit = fontDimensionResource(id = R.dimen.sp14),
    color: Color = black,
    maxLines: Int = 2
) {
    Text(
        text = text, style = TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight(300),
            fontSize = textSize,
            color = color,
        ), maxLines = maxLines, overflow = TextOverflow.Ellipsis
    )
}
