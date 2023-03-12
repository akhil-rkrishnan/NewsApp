package app.android.newsapp.ui.screens.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.android.newsapp.R
import app.android.newsapp.data.models.response.NewsResponse
import app.android.newsapp.ui.common_components.*
import app.android.newsapp.ui.screens.landing.navigation.LandingRoutes
import app.android.newsapp.ui.theme.black
import app.android.newsapp.ui.theme.orange
import app.android.newsapp.ui.theme.red
import app.android.newsapp.ui.utils.fontDimensionResource
import app.android.newsapp.utils.ErrorBody
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * Composable for News list screen
 * @param viewModel [LandingViewModel]
 * @param hasNetwork [Boolean] describes if the device has active connection or not.
 * @param navController [NavHostController] controller for the nav graph
 **/
@Composable
fun NewsListScreen(
    viewModel: LandingViewModel,
    hasNetwork: Boolean,
    navController: NavHostController
) {
    val newsState by viewModel.newsState.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val errorBody = newsState.apiErrorBody

    LaunchedEffect(key1 = hasNetwork, block = {
        if (hasNetwork && newsState.articles.isEmpty() && !newsState.isLoading) {
            viewModel.loadNewsList()
        }
    })

    AnimatedVisibility(
        visible = newsState.isLoading,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box {
            LinearProgressbar(
                modifier = Modifier.align(Alignment.Center),
                color = black,
                loadingLabel = stringResource(R.string.loadingLabel)
            )
        }
    }

    AnimatedVisibility(
        visible = !newsState.isLoading && newsState.articles.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.loadNewsList()
        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .padding(
                        vertical = dimensionResource(
                            id = R.dimen.dp25
                        ), horizontal = dimensionResource(id = R.dimen.dp10)
                    )
            ) {
                TitleText(text = newsState.providerName)
                if (!hasNetwork) {
                    VerticalSpacer(space = dimensionResource(id = R.dimen.dp10))
                    NewsErrorText(
                        text = stringResource(id = R.string.networkNotAvailable),
                        color = orange, maxLines = 4
                    )
                }
                VerticalSpacer(space = dimensionResource(id = R.dimen.dp20))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp10))) {
                    itemsIndexed(newsState.articles) { index, item ->
                        NewsCard(
                            isLastItem = index == newsState.articles.size - 1,
                            article = item
                        ) {
                            viewModel.setSelectedArticle(item)
                            navController.navigate(LandingRoutes.NewsDetailsRoute)
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = errorBody != null,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        errorBody?.let {
            ErrorScreen(it)
        }
    }

    AnimatedVisibility(
        visible = !hasNetwork && newsState.articles.isEmpty(),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(contentAlignment = Alignment.Center) {
            NewsErrorText(
                text = stringResource(id = R.string.networkNotAvailableEmpty),
                color = red
            )
        }

    }
}

/**
 * Composable for News card item
 * @param isLastItem [Boolean] if the item is last in the list
 * @param article [NewsResponse.Article] Article data
 * @param onNewsClick will be called when user clicks on the card
 **/
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

/**
 * Composable for News Headline
 * @param text headline of the news
 **/
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

/**
 * Composable for the error screen
 * @param errorBody [ErrorBody] Error thrown from network
 **/
@Composable
fun ErrorScreen(errorBody: ErrorBody) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(
                        id = R.dimen.dp25
                    )
                )
                .align(Alignment.Center)
        ) {
            NewsErrorText(
                text = stringResource(id = R.string.errorTitle), textSize = fontDimensionResource(
                    id = R.dimen.sp24
                ), color = red
            )
            NewsErrorText(text = stringResource(id = R.string.errorStatus, errorBody.status))
            NewsErrorText(text = stringResource(id = R.string.errorCode, errorBody.code))
            NewsErrorText(text = stringResource(id = R.string.errorMessage, errorBody.message))
        }
    }
}
