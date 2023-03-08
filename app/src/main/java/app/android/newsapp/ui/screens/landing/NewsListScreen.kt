package app.android.newsapp.ui.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.android.newsapp.BuildConfig
import app.android.newsapp.R
import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.ui.common_components.NetworkImage
import app.android.newsapp.ui.common_components.TitleText
import app.android.newsapp.ui.common_components.VerticalSpacer
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes
import app.android.newsapp.ui.utils.fontDimensionResource

@Composable
fun NewsListScreen(viewModel: LandingViewModel, navController: NavHostController) {

    val list by viewModel.newsList.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(
                top = dimensionResource(
                    id = R.dimen.dp25
                )
            )
    ) {
        TitleText(text = BuildConfig.SOURCE)
        VerticalSpacer(space = 25.dp)
        LazyColumn {
            itemsIndexed(list) { index, item ->
                NewsCard(isLastItem = index == list.size - 1, article = item) {
                    viewModel.setSelectedArticle(item)
                    navController.navigate(LandingRoutes.NewsDetails)
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    isLastItem: Boolean,
    article: NewsResponse.Article,
    onNewsClick: (NewsResponse.Article) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                bottom = if (isLastItem) dimensionResource(id = R.dimen.dp50) else dimensionResource(
                    id = R.dimen.dp8
                )
            )
            .padding(horizontal = dimensionResource(id = R.dimen.dp10))
            .clip(RoundedCornerShape(3))
            .background(color = Color.White, shape = RoundedCornerShape(5))
            .clickable {
                onNewsClick(article)
            }
    ) {
        Column(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dp10))
        ) {
            NewsHeadLine(article.title)
            VerticalSpacer(space = dimensionResource(id = R.dimen.dp10))
            NetworkImage(imageUrl = article.urlToImage)
        }
    }
}

@Composable
fun NewsHeadLine(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
            fontWeight = FontWeight(400),
            fontSize = fontDimensionResource(id = R.dimen.sp18),
            color = Color.Black
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 2, modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp5))
    )
}