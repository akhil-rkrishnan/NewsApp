package app.android.newsapp.ui.screens.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import app.android.newsapp.R
import app.android.newsapp.ui.common_components.*
import app.android.newsapp.ui.theme.grey
import app.android.newsapp.ui.theme.lightBlack
import app.android.newsapp.utils.toDescriptionOrDefault
import app.android.newsapp.utils.toTitleOrDefault

/**
 * Composable for News detail screen
 * @param viewModel [LandingViewModel]
 * @param navController [NavHostController]
 **/
@Composable
fun NewsDetailsScreen(viewModel: LandingViewModel, navController: NavHostController) {
    val article by remember {
        mutableStateOf(viewModel.newsState.value.selectedArticle)
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = dimensionResource(id = R.dimen.dp20))
            .verticalScroll(scrollState)
    ) {
        NetworkImage(imageUrl = article?.urlToImage)
        VerticalSpacer(space = dimensionResource(id = R.dimen.dp10))
        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.dp10)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp8))
        ) {
            TitleText(text = article?.title.toTitleOrDefault())
            NewsText(text = article?.description.toDescriptionOrDefault(), color = lightBlack)
            NewsTextLight(text = article?.content.toDescriptionOrDefault(), color = grey)
        }
    }


}