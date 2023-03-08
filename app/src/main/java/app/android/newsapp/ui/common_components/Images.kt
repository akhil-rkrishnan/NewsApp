package app.android.newsapp.ui.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import app.android.newsapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp5)))
        .zIndex(2f),
    imageUrl: String?,
    showPlaceHolders: Boolean = true,
    placeHolder: Int = R.drawable.placeholder,
    contentScale: ContentScale = ContentScale.Crop,
    alignment: Alignment = Alignment.Center,
    contentDescription: String = "image",
    showTransition: Boolean = true,
) {
    val context = LocalContext.current
    AsyncImage(
        model = if (showTransition) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build()
        } else imageUrl,
        contentDescription = contentDescription,
        placeholder = if (showPlaceHolders) painterResource(id = placeHolder) else null,
        error = if (showPlaceHolders) painterResource(id = placeHolder) else null,
        modifier = modifier,
        contentScale = contentScale,
        alignment = alignment
    )

}